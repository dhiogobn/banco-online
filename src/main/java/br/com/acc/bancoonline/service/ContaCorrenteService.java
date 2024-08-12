package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.dto.ContaCorrenteDTO;
import br.com.acc.bancoonline.dto.ExtratoDTO;
import br.com.acc.bancoonline.enums.OperacaoEnum;
import br.com.acc.bancoonline.exceptions.*;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import br.com.acc.bancoonline.repository.ExtratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {
    private final ContaCorrenteRepository repository;
    private final ExtratoRepository extratoRepository;

    private final ClienteService clienteService;

    private final ExtratoService extratoService;
    
    public void create(ContaCorrenteDTO contaCorrenteDTO) throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        if (Objects.isNull(contaCorrenteDTO.getAgencia()) && Objects.isNull(contaCorrenteDTO.getNumero()) ){
            throw new CampoVazioGenericoException();
        }
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setNumero(contaCorrenteDTO.getNumero());
        contaCorrente.setAgencia(contaCorrenteDTO.getAgencia());
        contaCorrente.setSaldo(contaCorrenteDTO.getSaldo());
        contaCorrente.setCliente(clienteService.findById(contaCorrenteDTO.getIdCliente()));

        repository.save(contaCorrente);
    }
    
    public ContaCorrente findById(int id) throws ContaCorrenteNaoEncontradaException {
        if (repository.findById(id).isEmpty()) {
            throw new ContaCorrenteNaoEncontradaException();
        }
        return repository.findById(id).get();
    }
    
    public List<ContaCorrente> findAll() {
        return repository.findAll();
    }
    
    public ContaCorrente update(int id, ContaCorrenteDTO newContaCorrenteDTO) throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        ContaCorrente contaCorrente = this.findById(id);
        if (Objects.isNull(newContaCorrenteDTO.getAgencia()) && Objects.isNull(newContaCorrenteDTO.getNumero()) ){
            throw new CampoVazioGenericoException();
        }
        contaCorrente.setAgencia(newContaCorrenteDTO.getAgencia());
        contaCorrente.setSaldo(newContaCorrenteDTO.getSaldo());
        contaCorrente.setNumero(newContaCorrenteDTO.getNumero());
        contaCorrente.setCliente(clienteService.findById(newContaCorrenteDTO.getIdCliente()));
        return repository.save(contaCorrente);
    }

    @Transactional
    public void deleteById(int id) throws ContaCorrenteNaoEncontradaException {
        ContaCorrente contaCorrente = this.findById(id);
        extratoRepository.deleteByContaCorrenteId(id);
        repository.delete(contaCorrente);
    }

    public double depositar(int idContaCorrente, double valor) throws DepositoInvalidoException, ContaCorrenteNaoEncontradaException, CampoVazioGenericoException {
        if (valor < 0) {
            throw new DepositoInvalidoException();
        }
        ContaCorrente contaCorrente = this.findById(idContaCorrente);
        contaCorrente.setSaldo(contaCorrente.getSaldo() + valor);
        ExtratoDTO extratoDTO = new ExtratoDTO();
        extratoDTO.setOperacao(OperacaoEnum.DEPOSITO.toString());
        extratoDTO.setValorOperacao(valor);
        extratoDTO.setDataHoraMovimento(LocalDateTime.now());
        extratoDTO.setIdContaCorrente(idContaCorrente);
        extratoService.create(extratoDTO);
        repository.save(contaCorrente);
        return contaCorrente.getSaldo();
    }

    public double sacar(int idContaCorrente, double valor) throws ContaCorrenteNaoEncontradaException, SaqueInvalidoException, CampoVazioGenericoException {
        ContaCorrente contaCorrente = this.findById(idContaCorrente);
        if (valor  > contaCorrente.getSaldo()) {
            throw new SaqueInvalidoException();
        }
        ExtratoDTO extratoDTO = new ExtratoDTO();
        extratoDTO.setOperacao(OperacaoEnum.SAQUE.toString());
        extratoDTO.setValorOperacao(valor);
        extratoDTO.setDataHoraMovimento(LocalDateTime.now());
        extratoDTO.setIdContaCorrente(idContaCorrente);
        extratoService.create(extratoDTO);
        contaCorrente.setSaldo(contaCorrente.getSaldo() - valor);
        repository.save(contaCorrente);
        return contaCorrente.getSaldo();
    }
    public double transferir(double valor, int idConta, String cpfDestinatario) throws ContaCorrenteNaoEncontradaException, SaqueInvalidoException, CampoVazioGenericoException {
        ContaCorrente conta = this.findById(idConta);
        ContaCorrente contaDestinatario = this.getContaByCpf(cpfDestinatario);
        if (valor > conta.getSaldo()) {
            throw new SaqueInvalidoException();
        }
        ExtratoDTO extratoDTO = new ExtratoDTO();
        extratoDTO.setOperacao(OperacaoEnum.TRANFERENCIA.toString());
        extratoDTO.setValorOperacao(valor);
        extratoDTO.setDataHoraMovimento(LocalDateTime.now());
        extratoDTO.setIdContaCorrente(idConta);
        extratoService.create(extratoDTO);
        conta.setSaldo(conta.getSaldo() - valor);
        contaDestinatario.setSaldo(contaDestinatario.getSaldo() + valor);
        repository.save(conta);
        repository.save(contaDestinatario);
        return conta.getSaldo();
    }

    public ContaCorrente getContaByCpf(String cpf) throws ContaCorrenteNaoEncontradaException {
        String cpfFormated = this.formatCpf(cpf);
        if (repository.findByClienteCpf(cpfFormated).isEmpty()) {
            throw new ContaCorrenteNaoEncontradaException();
        }
        return repository.findByClienteCpf(cpfFormated).get();
    }

    public String formatCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }


}

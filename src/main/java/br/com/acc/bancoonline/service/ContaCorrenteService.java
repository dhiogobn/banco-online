package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.dto.ContaCorrenteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {
    private final ContaCorrenteRepository repository;

    private final ExtratoService extratoService;
    
    public void create(ContaCorrenteDTO contaCorrenteDTO) throws CampoVazioGenericoException {
        if (Objects.isNull(contaCorrenteDTO.getAgencia()) && Objects.isNull(contaCorrenteDTO.getNumero()) ){
            throw new CampoVazioGenericoException();
        }
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setNumero(contaCorrenteDTO.getNumero());
        contaCorrente.setAgencia(contaCorrenteDTO.getAgencia());
        contaCorrente.setSaldo(contaCorrenteDTO.getSaldo());
        List<Extrato> extratos = new ArrayList<>();
        if (contaCorrenteDTO.getExtratoIds().size() > 0) {
            contaCorrenteDTO.getExtratoIds().forEach(extratoId -> extratos.add(extratoService.findById(extratoId)));
            contaCorrente.setExtratos(extratos);
        }

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
    
    public ContaCorrente update(int id, ContaCorrenteDTO newContaCorrenteDTO) throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException {
        ContaCorrente contaCorrente = this.findById(id);
        if (Objects.isNull(newContaCorrenteDTO.getAgencia()) && Objects.isNull(newContaCorrenteDTO.getNumero()) ){
            throw new CampoVazioGenericoException();
        }
        contaCorrente.setAgencia(newContaCorrenteDTO.getAgencia());
        contaCorrente.setSaldo(newContaCorrenteDTO.getSaldo());
        contaCorrente.setNumero(newContaCorrenteDTO.getNumero());
        List<Extrato> extratos = new ArrayList<>();
        if (newContaCorrenteDTO.getExtratoIds().size() > 0) {
            newContaCorrenteDTO.getExtratoIds().forEach(extratoId -> extratos.add(extratoService.findById(extratoId)));
            contaCorrente.setExtratos(extratos);
        }
        repository.save(contaCorrente);
        return contaCorrente;
    }
    
    public void deleteById(int id) throws ContaCorrenteNaoEncontradaException {
        ContaCorrente contaCorrente = this.findById(id);
        repository.delete(contaCorrente);
    }
}

package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.dto.ExtratoDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.ExtratoNaoEncontradoException;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import br.com.acc.bancoonline.repository.ExtratoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ExtratoService {
    private final ExtratoRepository repository;
    private final ContaCorrenteRepository contaCorrenteRepository;
    
    public void create(ExtratoDTO extratoDTO) throws CampoVazioGenericoException, ContaCorrenteNaoEncontradaException {
        if (Objects.isNull(extratoDTO.getDataHoraMovimento()) || Objects.isNull(extratoDTO.getOperacao())) {
            throw new CampoVazioGenericoException();
        }

        Extrato extrato = new Extrato();
        extrato.setDataHoraMovimento(LocalDateTime.now());
        extrato.setOperacao(extratoDTO.getOperacao());
        extrato.setValorOperacao(extratoDTO.getValorOperacao());
        extrato.setContaCorrente(getContaCorrenteById(extratoDTO.getIdContaCorrente()));

        repository.save(extrato);
    }

    private ContaCorrente getContaCorrenteById(int id) throws ContaCorrenteNaoEncontradaException {
        if (contaCorrenteRepository.findById(id).isEmpty()) {
            throw new ContaCorrenteNaoEncontradaException();
        }
        return contaCorrenteRepository.findById(id).get();
    }
    public Extrato findById(int id) throws ExtratoNaoEncontradoException {
        if (repository.findById(id).isEmpty()) {
            throw new ExtratoNaoEncontradoException();
        }
        return repository.findById(id).get();
    }
    
    public List<Extrato> findAll() {
        return repository.findAll();
    }
    
    public Extrato update(int id, ExtratoDTO newExtratoDTO) throws ExtratoNaoEncontradoException, CampoVazioGenericoException, ContaCorrenteNaoEncontradaException {
        Extrato extrato = this.findById(id);
        if (Objects.isNull(newExtratoDTO.getDataHoraMovimento()) || Objects.isNull(newExtratoDTO.getOperacao())) {
            throw new CampoVazioGenericoException();
        }
        extrato.setOperacao(newExtratoDTO.getOperacao());
        extrato.setValorOperacao(newExtratoDTO.getValorOperacao());
        extrato.setDataHoraMovimento(newExtratoDTO.getDataHoraMovimento());
        extrato.setContaCorrente(getContaCorrenteById(newExtratoDTO.getIdContaCorrente()));


        return repository.save(extrato);
    }
    
    public void deleteById(int id) throws ExtratoNaoEncontradoException {
        Extrato extrato = this.findById(id);
        repository.delete(extrato);
    }

    public List<Extrato> findAllByContaCorrenteId(int contaCorrenteId) throws ExtratoNaoEncontradoException {
        if (repository.findByContaCorrenteId(contaCorrenteId).isEmpty()) {
            throw new ExtratoNaoEncontradoException();
        }
        return repository.findByContaCorrenteId(contaCorrenteId).get();
    }
}

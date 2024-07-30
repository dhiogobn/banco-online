package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.repository.ExtratoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExtratoService {
    private final ExtratoRepository repository;
    
    public void create(Extrato extrato) {
        repository.save(extrato);
    }
    
    public Extrato findById(int id) {
        return repository.findById(id).get();
    }
    
    public List<Extrato> findAll() {
        return repository.findAll();
    }
    
    public Extrato update(int id, Extrato newExtrato) {
        Extrato extrato = this.findById(id);
        extrato.setOperacao(newExtrato.getOperacao());
        extrato.setValorOperacao(newExtrato.getValorOperacao());
        extrato.setDataHoraMovimento(newExtrato.getDataHoraMovimento());
        repository.save(extrato);
        return extrato;
    }
    
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}

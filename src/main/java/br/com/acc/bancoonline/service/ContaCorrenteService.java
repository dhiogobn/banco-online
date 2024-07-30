package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContaCorrenteService {
    private final ContaCorrenteRepository repository;
    
    public void create(ContaCorrente contaCorrente) {
        repository.save(contaCorrente);
    }
    
    public ContaCorrente findById(int id) {
        return repository.findById(id).get();
    }
    
    public List<ContaCorrente> findAll() {
        return repository.findAll();
    }
    
    public ContaCorrente update(int id, ContaCorrente newContaCorrente) {
        ContaCorrente contaCorrente = this.findById(id);
        contaCorrente.setAgencia(newContaCorrente.getAgencia());
        contaCorrente.setSaldo(newContaCorrente.getSaldo());
        contaCorrente.setNumero(newContaCorrente.getNumero());
        repository.save(contaCorrente);
        return contaCorrente;
    }
    
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}

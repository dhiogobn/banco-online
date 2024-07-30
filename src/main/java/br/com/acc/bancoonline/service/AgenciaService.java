package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.repository.AgenciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AgenciaService {
    private final AgenciaRepository repository;

    public void create(Agencia agencia) {
        repository.save(agencia);
    }

    public Agencia findById(int id) {
        return repository.findById(id).get();
    }

    public List<Agencia> findAll() {
        return repository.findAll();
    }

    public Agencia update(int id, Agencia newAgencia) {
        Agencia agencia = this.findById(id);
        agencia.setNomeAgencia(newAgencia.getNomeAgencia());
        agencia.setEndereco(newAgencia.getEndereco());
        agencia.setTelefone(newAgencia.getTelefone());
        repository.save(agencia);
        return agencia;
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}

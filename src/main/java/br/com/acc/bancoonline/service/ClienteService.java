package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    
    public void create(Cliente cliente) {
        repository.save(cliente);
    }
    
    public Cliente findById(int id) {
        return repository.findById(id).get();
    }
    
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    public Cliente update(int id, Cliente newCliente) {
        Cliente cliente = this.findById(id);
        cliente.setCpf(newCliente.getCpf());
        cliente.setNome(newCliente.getNome());
        cliente.setTelefone(newCliente.getTelefone());
        repository.save(cliente);
        return cliente;
    }
    
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}

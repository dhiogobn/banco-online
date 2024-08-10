package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.dto.ClienteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.exceptions.CpfInvalidoException;
import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ClienteDTO clienteDTO) throws CampoVazioGenericoException, CpfInvalidoException {
        service.create(clienteDTO);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable int id) throws ClienteNaoEncontradoException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/unique/{cpf}")
    public ResponseEntity<Cliente> findById(@PathVariable String cpf) throws ClienteNaoEncontradoException {
        try {
            return ResponseEntity.ok(service.findByCpf(cpf));
        } catch (ClienteNaoEncontradoException error) {
            return  ResponseEntity.notFound().build();
        }

    }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable int id, @RequestBody ClienteDTO cliente) throws CampoVazioGenericoException, CpfInvalidoException, ClienteNaoEncontradoException {
        return ResponseEntity.ok(service.update(id, cliente));        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) throws ClienteNaoEncontradoException {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

package br.com.acc.bancoonline.controller;

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
    public ResponseEntity<Void> create(@RequestBody Cliente cliente) {
        service.create(cliente);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable int id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.update(id, cliente));        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

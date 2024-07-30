package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.service.ContaCorrenteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/contaCorrentes")
@AllArgsConstructor
public class ContaCorrenteController {

    private final ContaCorrenteService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ContaCorrente contaCorrente) {
        service.create(contaCorrente);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrente> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ContaCorrente>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContaCorrente> update(@PathVariable int id, @RequestBody ContaCorrente contaCorrente) {
        return ResponseEntity.ok(service.update(id, contaCorrente));        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

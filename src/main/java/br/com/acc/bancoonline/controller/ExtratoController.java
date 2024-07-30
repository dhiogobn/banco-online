package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.service.ExtratoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/extratos")
@AllArgsConstructor
public class ExtratoController {

    private final ExtratoService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Extrato extrato) {
        service.create(extrato);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Extrato> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Extrato>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Extrato> update(@PathVariable int id, @RequestBody Extrato extrato) {
        return ResponseEntity.ok(service.update(id, extrato));        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.service.AgenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/agencias")
@AllArgsConstructor
public class AgenciaController {

    private final AgenciaService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Agencia agencia) {
        service.create(agencia);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agencia> findById(@PathVariable int id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Agencia>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agencia> update(@PathVariable int id, @RequestBody Agencia agencia) {
        return ResponseEntity.ok(service.update(id, agencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

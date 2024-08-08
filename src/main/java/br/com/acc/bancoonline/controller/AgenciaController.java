package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.dto.AgenciaDTO;
import br.com.acc.bancoonline.exceptions.AgenciaNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
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
    public ResponseEntity<Void> create(@RequestBody AgenciaDTO agenciaDTO) throws CampoVazioGenericoException {
        service.create(agenciaDTO);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agencia> findById(@PathVariable int id) throws AgenciaNaoEncontradaException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Agencia>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agencia> update(@PathVariable int id, @RequestBody AgenciaDTO agenciaDTO) throws CampoVazioGenericoException, AgenciaNaoEncontradaException {
        return ResponseEntity.ok(service.update(id, agenciaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) throws AgenciaNaoEncontradaException {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}

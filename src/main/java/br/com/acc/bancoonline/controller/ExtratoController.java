package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.dto.ExtratoDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.ExtratoNaoEncontradoException;
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
    public ResponseEntity<Void> create(@RequestBody ExtratoDTO extratoDTO) throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException {
        service.create(extratoDTO);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Extrato> findById(@PathVariable int id) throws ExtratoNaoEncontradoException {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<Extrato>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Extrato> update(@PathVariable int id, @RequestBody ExtratoDTO extratoDTO) throws ExtratoNaoEncontradoException, ContaCorrenteNaoEncontradaException, CampoVazioGenericoException {
        return ResponseEntity.ok(service.update(id, extratoDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) throws ExtratoNaoEncontradoException {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/extratosDaConta/{ContaCorrenteId}")
    public ResponseEntity<List<Extrato>> findAllByContaCorrenteId(@PathVariable int ContaCorrenteId) throws ExtratoNaoEncontradoException {
        return ResponseEntity.ok(service.findAllByContaCorrenteId(ContaCorrenteId));
    }
}

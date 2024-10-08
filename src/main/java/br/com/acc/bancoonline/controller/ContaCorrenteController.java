package br.com.acc.bancoonline.controller;

import br.com.acc.bancoonline.dto.ContaCorrenteDTO;
import br.com.acc.bancoonline.exceptions.*;
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
    public ResponseEntity<Void> create(@RequestBody ContaCorrenteDTO contaCorrente) throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        service.create(contaCorrente);
        return new ResponseEntity<>(CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContaCorrente> findById(@PathVariable int id) throws ContaCorrenteNaoEncontradaException {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ContaCorrente>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContaCorrente> update(@PathVariable int id, @RequestBody ContaCorrenteDTO contaCorrente) throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        return ResponseEntity.ok(service.update(id, contaCorrente));        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) throws ContaCorrenteNaoEncontradaException {
        service.deleteById(id);
        return new ResponseEntity<>(OK);
    }
    @GetMapping("/consultarDadosDaConta/{cpf}")
    public ResponseEntity<ContaCorrente> consultarDadosDaConta(@PathVariable String cpf) throws ContaCorrenteNaoEncontradaException {
        return ResponseEntity.ok(service.getContaByCpf(cpf));
    }

    @PatchMapping("/depositar/{idContaCorrente}/{valor}")
    public ResponseEntity<Double> depositar(@PathVariable int idContaCorrente, @PathVariable double valor) throws ContaCorrenteNaoEncontradaException, DepositoInvalidoException, CampoVazioGenericoException {
        return ResponseEntity.ok(service.depositar(idContaCorrente, valor));
    }

    @PatchMapping("/sacar/{idContaCorrente}/{valor}")
    public ResponseEntity<Double> sacar(@PathVariable int idContaCorrente, @PathVariable double valor) throws ContaCorrenteNaoEncontradaException, SaqueInvalidoException, CampoVazioGenericoException {
        return ResponseEntity.ok(service.sacar(idContaCorrente, valor));
    }

    @PatchMapping("/transferir/{valor}/{idConta}/{cpfDestinatario}")
    public ResponseEntity<Double> tranferir(@PathVariable double valor,@PathVariable int idConta,@PathVariable String cpfDestinatario) throws ContaCorrenteNaoEncontradaException, SaqueInvalidoException, CampoVazioGenericoException {
        return ResponseEntity.ok(service.transferir(valor, idConta, cpfDestinatario));
    }

}

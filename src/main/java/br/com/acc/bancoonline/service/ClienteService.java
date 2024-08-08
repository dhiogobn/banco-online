package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.dto.ClienteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.CpfInvalidoException;
import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.repository.AgenciaRepository;
import br.com.acc.bancoonline.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    private final AgenciaService agenciaService;

    private final ContaCorrenteService contaCorrenteService;
    
    public void create(ClienteDTO clienteDTO) throws CampoVazioGenericoException, CpfInvalidoException {
        if (Objects.isNull(clienteDTO.getNome()) && Objects.isNull(clienteDTO.getTelefone()) && Objects.isNull(clienteDTO.getCpf())) {
            throw new CampoVazioGenericoException();
        }
        if (!validaCpf(clienteDTO.getCpf())) {
            throw new CpfInvalidoException();
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setCpf(clienteDTO.getCpf());
        List<Agencia> agencias = cliente.getAgencias();
        List< ContaCorrente> contaCorrentes = cliente.getContaCorrentes();
        if (clienteDTO.getAgenciaIds().size() > 0) {
            clienteDTO.getAgenciaIds().forEach(id -> agencias.add(agenciaService.findById(id)));
            cliente.setAgencias(agencias);

        }
        if (clienteDTO.getContaCorrenteIds().size() > 0) {
            clienteDTO.getContaCorrenteIds().forEach(id -> {
                try {
                    contaCorrentes.add(contaCorrenteService.findById(id));
                } catch (ContaCorrenteNaoEncontradaException e) {
                    throw new RuntimeException(e);
                }
            });
            cliente.setContaCorrentes(contaCorrentes);
        }
        repository.save(cliente);
    }

    public boolean validaCpf(String cpf) {
        // Remover caracteres especiais
        cpf = cpf.replaceAll("\\D", "");

        // Verificar se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verificar se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcular o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);
        if (firstVerifier >= 10) {
            firstVerifier = 0;
        }

        // Verificar o primeiro dígito verificador
        if (firstVerifier != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Calcular o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);
        if (secondVerifier >= 10) {
            secondVerifier = 0;
        }

        // Verificar o segundo dígito verificador
        if (secondVerifier != (cpf.charAt(10) - '0')) {
            return false;
        }

        return true;
    }
    
    public Cliente findById(int id) throws ClienteNaoEncontradoException {
        if (repository.findById(id).isEmpty()) {
            throw new ClienteNaoEncontradoException();
        }
        return repository.findById(id).get();
    }
    
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    public Cliente update(int id, ClienteDTO newClienteDTO) throws ClienteNaoEncontradoException, CampoVazioGenericoException, CpfInvalidoException {
        if (Objects.isNull(newClienteDTO.getNome()) && Objects.isNull(newClienteDTO.getTelefone()) && Objects.isNull(newClienteDTO.getCpf())) {
            throw new CampoVazioGenericoException();
        }
        if (!validaCpf(newClienteDTO.getCpf())) {
            throw new CpfInvalidoException();
        }
        Cliente cliente = this.findById(id);
        cliente.setCpf(newClienteDTO.getCpf());
        cliente.setNome(newClienteDTO.getNome());
        cliente.setTelefone(newClienteDTO.getTelefone());
        List<Agencia> agencias = cliente.getAgencias();
        List< ContaCorrente> contaCorrentes = cliente.getContaCorrentes();
        if (newClienteDTO.getAgenciaIds().size() > 0) {
            newClienteDTO.getAgenciaIds().forEach(agenciaId -> agencias.add(agenciaService.findById(agenciaId)));
            cliente.setAgencias(agencias);

        }
        if (newClienteDTO.getContaCorrenteIds().size() > 0) {
            newClienteDTO.getContaCorrenteIds().forEach(contaCorrenteId -> {
                try {
                    contaCorrentes.add(contaCorrenteService.findById(contaCorrenteId));
                } catch (ContaCorrenteNaoEncontradaException e) {
                    throw new RuntimeException(e);
                }
            });
            cliente.setContaCorrentes(contaCorrentes);
        }

        return repository.save(cliente);
    }
    
    public void deleteById(int id) throws ClienteNaoEncontradoException {
        Cliente cliente = findById(id);
        repository.delete(cliente);
    }
}

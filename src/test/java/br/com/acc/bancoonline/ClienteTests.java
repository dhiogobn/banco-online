package br.com.acc.bancoonline;

import br.com.acc.bancoonline.dto.ClienteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.exceptions.CpfInvalidoException;
import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.repository.ClienteRepository;
import br.com.acc.bancoonline.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteTests {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteDTO = new ClienteDTO();
        clienteDTO.setNome("John Doe");
        clienteDTO.setCpf("12345678909");
        clienteDTO.setTelefone("123456789");

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("John Doe");
        cliente.setCpf("12345678909");
        cliente.setTelefone("123456789");
    }

    @Test
    void testCreateValidCliente() throws CampoVazioGenericoException, CpfInvalidoException {
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        service.create(clienteDTO);

        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testCreateWithEmptyFieldsThrowsCampoVazioGenericoException() {
        ClienteDTO emptyClienteDTO = new ClienteDTO();

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.create(emptyClienteDTO);
        });
    }

    @Test
    void testCreateWithInvalidCpfThrowsCpfInvalidoException() {
        clienteDTO.setCpf("12345678900");

        assertThrows(CpfInvalidoException.class, () -> {
            service.create(clienteDTO);
        });
    }

    @Test
    void testFindByIdExistingCliente() throws ClienteNaoEncontradoException {
        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente foundCliente = service.findById(1);

        assertEquals(cliente, foundCliente);
    }

    @Test
    void testFindByIdNonExistingClienteThrowsClienteNaoEncontradoException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            service.findById(1);
        });
    }

    @Test
    void testFindAll() {
        List<Cliente> clientes = List.of(cliente);
        when(repository.findAll()).thenReturn(clientes);

        List<Cliente> foundClientes = service.findAll();

        assertEquals(clientes, foundClientes);
    }

    @Test
    void testUpdateValidCliente() throws ClienteNaoEncontradoException, CampoVazioGenericoException, CpfInvalidoException {
        when(repository.findById(1)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updatedCliente = service.update(1, clienteDTO);

        assertEquals(clienteDTO.getNome(), updatedCliente.getNome());
        assertEquals(clienteDTO.getCpf(), updatedCliente.getCpf());
        assertEquals(clienteDTO.getTelefone(), updatedCliente.getTelefone());
    }

    @Test
    void testUpdateWithEmptyFieldsThrowsCampoVazioGenericoException() throws ClienteNaoEncontradoException {
        ClienteDTO emptyClienteDTO = new ClienteDTO();

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.update(1, emptyClienteDTO);
        });
    }

    @Test
    void testUpdateWithInvalidCpfThrowsCpfInvalidoException() throws ClienteNaoEncontradoException {
        clienteDTO.setCpf("12345678900");

        assertThrows(CpfInvalidoException.class, () -> {
            service.update(1, clienteDTO);
        });
    }

    @Test
    void testDeleteById() throws ClienteNaoEncontradoException {
        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        service.deleteById(1);

        verify(repository, times(1)).delete(cliente);
    }
}

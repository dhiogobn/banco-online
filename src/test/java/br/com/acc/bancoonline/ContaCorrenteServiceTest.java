package br.com.acc.bancoonline;
import br.com.acc.bancoonline.dto.ContaCorrenteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import br.com.acc.bancoonline.service.ClienteService;
import br.com.acc.bancoonline.service.ContaCorrenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ContaCorrenteServiceTest {

    @Mock
    private ContaCorrenteRepository repository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ContaCorrenteService service;

    private ContaCorrenteDTO contaCorrenteDTO;
    private ContaCorrente contaCorrente;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contaCorrenteDTO = new ContaCorrenteDTO();
        contaCorrenteDTO.setNumero("12345");
        contaCorrenteDTO.setAgencia("001");
        contaCorrenteDTO.setSaldo(1000.00);
        contaCorrenteDTO.setIdCliente(1);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("John Doe");

        contaCorrente = new ContaCorrente();
        contaCorrente.setId(1);
        contaCorrente.setNumero("12345");
        contaCorrente.setAgencia("001");
        contaCorrente.setSaldo(1000.00);
        contaCorrente.setCliente(cliente);
    }

    @Test
    void testCreateValidContaCorrente() throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        when(clienteService.findById(1)).thenReturn(cliente);
        when(repository.save(any(ContaCorrente.class))).thenReturn(contaCorrente);

        service.create(contaCorrenteDTO);

        verify(repository, times(1)).save(any(ContaCorrente.class));
    }

    @Test
    void testCreateWithEmptyFieldsThrowsCampoVazioGenericoException() {
        ContaCorrenteDTO emptyContaCorrenteDTO = new ContaCorrenteDTO();

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.create(emptyContaCorrenteDTO);
        });
    }

    @Test
    void testCreateWithInvalidClientThrowsClienteNaoEncontradoException() throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        when(clienteService.findById(1)).thenThrow(new ClienteNaoEncontradoException());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            service.create(contaCorrenteDTO);
        });
    }

    @Test
    void testFindByIdExistingContaCorrente() throws ContaCorrenteNaoEncontradaException {
        when(repository.findById(1)).thenReturn(Optional.of(contaCorrente));

        ContaCorrente foundContaCorrente = service.findById(1);

        assertEquals(contaCorrente, foundContaCorrente);
    }

    @Test
    void testFindByIdNonExistingContaCorrenteThrowsContaCorrenteNaoEncontradaException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ContaCorrenteNaoEncontradaException.class, () -> {
            service.findById(1);
        });
    }

    @Test
    void testFindAll() {
        List<ContaCorrente> contas = List.of(contaCorrente);
        when(repository.findAll()).thenReturn(contas);

        List<ContaCorrente> foundContas = service.findAll();

        assertEquals(contas, foundContas);
    }

    @Test
    void testUpdateValidContaCorrente() throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        when(repository.findById(1)).thenReturn(Optional.of(contaCorrente));
        when(clienteService.findById(1)).thenReturn(cliente);
        when(repository.save(any(ContaCorrente.class))).thenReturn(contaCorrente);

        ContaCorrente updatedContaCorrente = service.update(1, contaCorrenteDTO);

        assertEquals(contaCorrenteDTO.getNumero(), updatedContaCorrente.getNumero());
        assertEquals(contaCorrenteDTO.getAgencia(), updatedContaCorrente.getAgencia());
        assertEquals(contaCorrenteDTO.getSaldo(), updatedContaCorrente.getSaldo());
        assertEquals(cliente, updatedContaCorrente.getCliente());
    }

    @Test
    void testUpdateWithEmptyFieldsThrowsCampoVazioGenericoException() throws ContaCorrenteNaoEncontradaException {
        ContaCorrenteDTO emptyContaCorrenteDTO = new ContaCorrenteDTO();
        when(repository.findById(1)).thenReturn(Optional.of(contaCorrente));

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.update(1, emptyContaCorrenteDTO);
        });
    }

    @Test
    void testUpdateWithInvalidClientThrowsClienteNaoEncontradoException() throws ContaCorrenteNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        when(repository.findById(1)).thenReturn(Optional.of(contaCorrente));
        when(clienteService.findById(1)).thenThrow(new ClienteNaoEncontradoException());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            service.update(1, contaCorrenteDTO);
        });
    }

    @Test
    void testDeleteById() throws ContaCorrenteNaoEncontradaException {
        when(repository.findById(1)).thenReturn(Optional.of(contaCorrente));

        service.deleteById(1);

        verify(repository, times(1)).delete(contaCorrente);
    }
}

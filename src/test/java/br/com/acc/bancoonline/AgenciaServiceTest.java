package br.com.acc.bancoonline;

import br.com.acc.bancoonline.dto.AgenciaDTO;
import br.com.acc.bancoonline.exceptions.AgenciaNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.repository.AgenciaRepository;
import br.com.acc.bancoonline.service.AgenciaService;
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

public class AgenciaServiceTest {

    @Mock
    private AgenciaRepository repository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private AgenciaService service;

    private AgenciaDTO agenciaDTO;
    private Agencia agencia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        agenciaDTO = new AgenciaDTO();
        agenciaDTO.setNomeAgencia("Agencia Central");
        agenciaDTO.setEndereco("Rua Principal, 123");
        agenciaDTO.setTelefone("1234-5678");

        agencia = new Agencia();
        agencia.setId(1);
        agencia.setNomeAgencia("Agencia Central");
        agencia.setEndereco("Rua Principal, 123");
        agencia.setTelefone("1234-5678");
    }

    @Test
    void testCreateValidAgencia() throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        when(clienteService.findById(anyInt())).thenReturn(null); // Assume que não há necessidade de cliente para criação de agência

        service.create(agenciaDTO);

        verify(repository, times(1)).save(any(Agencia.class));
    }

    @Test
    void testCreateWithEmptyFieldsThrowsCampoVazioGenericoException() {
        AgenciaDTO emptyAgenciaDTO = new AgenciaDTO();

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.create(emptyAgenciaDTO);
        });
    }

    @Test
    void testFindByIdExistingAgencia() throws AgenciaNaoEncontradaException {
        when(repository.findById(1)).thenReturn(Optional.of(agencia));

        Agencia foundAgencia = service.findById(1);

        assertEquals(agencia, foundAgencia);
    }

    @Test
    void testFindByIdNonExistingAgenciaThrowsAgenciaNaoEncontradaException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AgenciaNaoEncontradaException.class, () -> {
            service.findById(1);
        });
    }

    @Test
    void testFindAll() {
        List<Agencia> agencias = List.of(agencia);
        when(repository.findAll()).thenReturn(agencias);

        List<Agencia> foundAgencias = service.findAll();

        assertEquals(agencias, foundAgencias);
    }

    @Test
    void testUpdateValidAgencia() throws AgenciaNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        AgenciaDTO newAgenciaDTO = new AgenciaDTO();
        newAgenciaDTO.setNomeAgencia("Agencia Nova");
        newAgenciaDTO.setEndereco("Rua Secundária, 456");
        newAgenciaDTO.setTelefone("9876-5432");

        when(repository.findById(1)).thenReturn(Optional.of(agencia));
        when(clienteService.findById(anyInt())).thenReturn(null); // Assume que não há necessidade de cliente para atualização de agência
        when(repository.save(any(Agencia.class))).thenReturn(agencia);

        Agencia updatedAgencia = service.update(1, newAgenciaDTO);

        assertEquals(newAgenciaDTO.getNomeAgencia(), updatedAgencia.getNomeAgencia());
        assertEquals(newAgenciaDTO.getEndereco(), updatedAgencia.getEndereco());
        assertEquals(newAgenciaDTO.getTelefone(), updatedAgencia.getTelefone());
    }

    @Test
    void testUpdateWithEmptyFieldsThrowsCampoVazioGenericoException() throws AgenciaNaoEncontradaException {
        AgenciaDTO emptyAgenciaDTO = new AgenciaDTO();
        when(repository.findById(1)).thenReturn(Optional.of(agencia));

        assertThrows(CampoVazioGenericoException.class, () -> {
            service.update(1, emptyAgenciaDTO);
        });
    }

    @Test
    void testDeleteById() throws AgenciaNaoEncontradaException {
        when(repository.findById(1)).thenReturn(Optional.of(agencia));

        service.deleteById(1);

        verify(repository, times(1)).delete(agencia);
    }
}

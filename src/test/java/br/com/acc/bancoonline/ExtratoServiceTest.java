package br.com.acc.bancoonline;
import br.com.acc.bancoonline.dto.ExtratoDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.ExtratoNaoEncontradoException;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.repository.ContaCorrenteRepository;
import br.com.acc.bancoonline.repository.ExtratoRepository;
import br.com.acc.bancoonline.service.ExtratoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExtratoServiceTest {

    @InjectMocks
    private ExtratoService extratoService;

    @Mock
    private ExtratoRepository extratoRepository;

    @Mock
    private ContaCorrenteRepository contaCorrenteRepository;

    private ExtratoDTO extratoDTO;
    private Extrato extrato;
    private ContaCorrente contaCorrente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        extratoDTO = new ExtratoDTO();
        extratoDTO.setDataHoraMovimento(LocalDateTime.now());
        extratoDTO.setOperacao("DEPOSITO");
        extratoDTO.setValorOperacao(500.0);
        extratoDTO.setIdContaCorrente(1);

        extrato = new Extrato();
        extrato.setId(1);
        extrato.setDataHoraMovimento(LocalDateTime.now());
        extrato.setOperacao("DEPOSITO");
        extrato.setValorOperacao(500.0);

        contaCorrente = new ContaCorrente();
        contaCorrente.setId(1);
    }

    @Test
    void testCreateExtrato() throws CampoVazioGenericoException, ContaCorrenteNaoEncontradaException {
        when(contaCorrenteRepository.findById(1)).thenReturn(Optional.of(contaCorrente));
        when(extratoRepository.save(any(Extrato.class))).thenReturn(extrato);

        extratoService.create(extratoDTO);

        verify(extratoRepository, times(1)).save(any(Extrato.class));
    }

    @Test
    void testCreateExtratoCampoVazio() throws ContaCorrenteNaoEncontradaException {
        extratoDTO.setDataHoraMovimento(null);

        assertThrows(CampoVazioGenericoException.class, () -> {
            extratoService.create(extratoDTO);
        });
    }

    @Test
    void testCreateExtratoContaCorrenteNaoEncontrada() throws CampoVazioGenericoException {
        when(contaCorrenteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ContaCorrenteNaoEncontradaException.class, () -> {
            extratoService.create(extratoDTO);
        });
    }

    @Test
    void testFindById() throws ExtratoNaoEncontradoException {
        when(extratoRepository.findById(1)).thenReturn(Optional.of(extrato));

        Extrato foundExtrato = extratoService.findById(1);

        assertEquals(extrato, foundExtrato);
    }

    @Test
    void testFindByIdNaoEncontrado() {
        when(extratoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ExtratoNaoEncontradoException.class, () -> {
            extratoService.findById(1);
        });
    }

    @Test
    void testFindAll() {
        List<Extrato> extratos = Arrays.asList(extrato);
        when(extratoRepository.findAll()).thenReturn(extratos);

        List<Extrato> foundExtratos = extratoService.findAll();

        assertEquals(1, foundExtratos.size());
        assertEquals(extrato, foundExtratos.get(0));
    }

    @Test
    void testUpdate() throws ExtratoNaoEncontradoException, CampoVazioGenericoException, ContaCorrenteNaoEncontradaException {
        when(extratoRepository.findById(1)).thenReturn(Optional.of(extrato));
        when(contaCorrenteRepository.findById(1)).thenReturn(Optional.of(contaCorrente));
        when(extratoRepository.save(any(Extrato.class))).thenReturn(extrato);

        extratoService.update(1, extratoDTO);

        verify(extratoRepository, times(1)).save(any(Extrato.class));
    }

    @Test
    void testUpdateCampoVazio() throws ExtratoNaoEncontradoException, ContaCorrenteNaoEncontradaException {
        extratoDTO.setOperacao(null);

        assertThrows(CampoVazioGenericoException.class, () -> {
            extratoService.update(1, extratoDTO);
        });
    }

    @Test
    void testUpdateExtratoNaoEncontrado() throws CampoVazioGenericoException, ContaCorrenteNaoEncontradaException {
        when(extratoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ExtratoNaoEncontradoException.class, () -> {
            extratoService.update(1, extratoDTO);
        });
    }

    @Test
    void testUpdateContaCorrenteNaoEncontrada() throws ExtratoNaoEncontradoException, CampoVazioGenericoException {
        when(extratoRepository.findById(1)).thenReturn(Optional.of(extrato));
        when(contaCorrenteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ContaCorrenteNaoEncontradaException.class, () -> {
            extratoService.update(1, extratoDTO);
        });
    }

    @Test
    void testDeleteById() throws ExtratoNaoEncontradoException {
        when(extratoRepository.findById(1)).thenReturn(Optional.of(extrato));

        extratoService.deleteById(1);

        verify(extratoRepository, times(1)).delete(extrato);
    }

    @Test
    void testDeleteByIdNaoEncontrado() {
        when(extratoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ExtratoNaoEncontradoException.class, () -> {
            extratoService.deleteById(1);
        });
    }

    @Test
    void testFindAllByContaCorrenteId() throws ExtratoNaoEncontradoException {
        List<Extrato> extratos = Arrays.asList(extrato);
        when(extratoRepository.findByContaCorrenteId(1)).thenReturn(Optional.of(extratos));

        List<Extrato> foundExtratos = extratoService.findAllByContaCorrenteId(1);

        assertEquals(1, foundExtratos.size());
        assertEquals(extrato, foundExtratos.get(0));
    }

    @Test
    void testFindAllByContaCorrenteIdNaoEncontrado() {
        when(extratoRepository.findByContaCorrenteId(1)).thenReturn(Optional.empty());

        assertThrows(ExtratoNaoEncontradoException.class, () -> {
            extratoService.findAllByContaCorrenteId(1);
        });
    }
}

package br.com.acc.bancoonline;

import br.com.acc.bancoonline.controller.ExtratoController;
import br.com.acc.bancoonline.dto.ExtratoDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ContaCorrenteNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.ExtratoNaoEncontradoException;
import br.com.acc.bancoonline.model.Extrato;
import br.com.acc.bancoonline.service.ExtratoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExtratoController.class)
public class ExtratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExtratoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ExtratoDTO extratoDTO;
    private Extrato extrato;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testCreateExtrato() throws Exception {
        doNothing().when(service).create(any(ExtratoDTO.class));

        mockMvc.perform(post("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateExtratoCampoVazio() throws Exception {
        extratoDTO.setDataHoraMovimento(null);

        mockMvc.perform(post("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateExtratoContaCorrenteNaoEncontrada() throws Exception {
        doThrow(new ContaCorrenteNaoEncontradaException()).when(service).create(any(ExtratoDTO.class));

        mockMvc.perform(post("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById() throws Exception {
        when(service.findById(1)).thenReturn(extrato);

        mockMvc.perform(get("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(extrato.getId())))
                .andExpect(jsonPath("$.operacao", is(extrato.getOperacao())))
                .andExpect(jsonPath("$.valorOperacao", is(extrato.getValorOperacao())));
    }

    @Test
    void testFindByIdNaoEncontrado() throws Exception {
        when(service.findById(1)).thenThrow(new ExtratoNaoEncontradoException());

        mockMvc.perform(get("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAll() throws Exception {
        List<Extrato> extratos = Arrays.asList(extrato);
        when(service.findAll()).thenReturn(extratos);

        mockMvc.perform(get("/api/extratos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(extrato.getId())))
                .andExpect(jsonPath("$[0].operacao", is(extrato.getOperacao())))
                .andExpect(jsonPath("$[0].valorOperacao", is(extrato.getValorOperacao())));
    }

    @Test
    void testUpdateExtrato() throws Exception {
        when(service.update(anyInt(), any(ExtratoDTO.class))).thenReturn(extrato);

        mockMvc.perform(put("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(extrato.getId())))
                .andExpect(jsonPath("$.operacao", is(extrato.getOperacao())))
                .andExpect(jsonPath("$.valorOperacao", is(extrato.getValorOperacao())));
    }

    @Test
    void testUpdateExtratoCampoVazio() throws Exception {
        extratoDTO.setOperacao(null);

        mockMvc.perform(put("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateExtratoNaoEncontrado() throws Exception {
        when(service.update(anyInt(), any(ExtratoDTO.class)))
                .thenThrow(new ExtratoNaoEncontradoException());

        mockMvc.perform(put("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateContaCorrenteNaoEncontrada() throws Exception {
        when(service.update(anyInt(), any(ExtratoDTO.class)))
                .thenThrow(new ContaCorrenteNaoEncontradaException());

        mockMvc.perform(put("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extratoDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByIdNaoEncontrado() throws Exception {
        doThrow(new ExtratoNaoEncontradoException()).when(service).deleteById(1);

        mockMvc.perform(delete("/api/extratos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllByContaCorrenteId() throws Exception {
        List<Extrato> extratos = Arrays.asList(extrato);
        when(service.findAllByContaCorrenteId(1)).thenReturn(extratos);

        mockMvc.perform(get("/api/extratos/extratosDaConta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(extrato.getId())))
                .andExpect(jsonPath("$[0].operacao", is(extrato.getOperacao())))
                .andExpect(jsonPath("$[0].valorOperacao", is(extrato.getValorOperacao())));
    }

    @Test
    void testFindAllByContaCorrenteIdNaoEncontrado() throws Exception {
        when(service.findAllByContaCorrenteId(1)).thenThrow(new ExtratoNaoEncontradoException());

        mockMvc.perform(get("/api/extratos/extratosDaConta/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

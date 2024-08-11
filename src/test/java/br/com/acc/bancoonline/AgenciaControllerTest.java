package br.com.acc.bancoonline;
import br.com.acc.bancoonline.controller.AgenciaController;
import br.com.acc.bancoonline.dto.AgenciaDTO;
import br.com.acc.bancoonline.exceptions.AgenciaNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.service.AgenciaService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AgenciaController.class)
public class AgenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgenciaService service;

    @Autowired
    private ObjectMapper objectMapper;

    private AgenciaDTO agenciaDTO;
    private Agencia agencia;

    @BeforeEach
    void setUp() {
        agenciaDTO = new AgenciaDTO();
        agenciaDTO.setNomeAgencia("Agência Central");
        agenciaDTO.setEndereco("Rua Principal, 123");
        agenciaDTO.setTelefone("1234-5678");

        agencia = new Agencia();
        agencia.setId(1);
        agencia.setNomeAgencia("Agência Central");
        agencia.setEndereco("Rua Principal, 123");
        agencia.setTelefone("1234-5678");
    }

    @Test
    void testCreateAgencia() throws Exception {
        doNothing().when(service).create(any(AgenciaDTO.class));

        mockMvc.perform(post("/api/agencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agenciaDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testFindById() throws Exception {
        when(service.findById(1)).thenReturn(agencia);

        mockMvc.perform(get("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(agencia.getId())))
                .andExpect(jsonPath("$.nomeAgencia", is(agencia.getNomeAgencia())))
                .andExpect(jsonPath("$.endereco", is(agencia.getEndereco())))
                .andExpect(jsonPath("$.telefone", is(agencia.getTelefone())));
    }

    @Test
    void testFindAllAgencias() throws Exception {
        List<Agencia> agencias = Arrays.asList(agencia);
        when(service.findAll()).thenReturn(agencias);

        mockMvc.perform(get("/api/agencias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(agencia.getId())))
                .andExpect(jsonPath("$[0].nomeAgencia", is(agencia.getNomeAgencia())))
                .andExpect(jsonPath("$[0].endereco", is(agencia.getEndereco())))
                .andExpect(jsonPath("$[0].telefone", is(agencia.getTelefone())));
    }

    @Test
    void testUpdateAgencia() throws Exception {
        when(service.update(eq(1), any(AgenciaDTO.class))).thenReturn(agencia);

        mockMvc.perform(put("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agenciaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(agencia.getId())))
                .andExpect(jsonPath("$.nomeAgencia", is(agencia.getNomeAgencia())))
                .andExpect(jsonPath("$.endereco", is(agencia.getEndereco())))
                .andExpect(jsonPath("$.telefone", is(agencia.getTelefone())));
    }

    @Test
    void testDeleteAgencia() throws Exception {
        doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/api/agencias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

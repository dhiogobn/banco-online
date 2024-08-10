package br.com.acc.bancoonline;

import br.com.acc.bancoonline.controller.ClienteController;
import br.com.acc.bancoonline.dto.ClienteDTO;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.exceptions.CpfInvalidoException;
import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.service.ClienteService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
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
    void testCreateCliente() throws Exception {
        doNothing().when(service).create(any(ClienteDTO.class));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testFindById() throws Exception {
        when(service.findById(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(cliente.getId())))
                .andExpect(jsonPath("$.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.cpf", is(cliente.getCpf())))
                .andExpect(jsonPath("$.telefone", is(cliente.getTelefone())));
    }

//    @Test
//    void testFindByIdNotFound() throws Exception {
//        when(service.findById(1)).thenThrow(new ClienteNaoEncontradoException());
//
//        mockMvc.perform(get("/api/clientes/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void testFindAllClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(service.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(cliente.getId())))
                .andExpect(jsonPath("$[0].nome", is(cliente.getNome())))
                .andExpect(jsonPath("$[0].cpf", is(cliente.getCpf())))
                .andExpect(jsonPath("$[0].telefone", is(cliente.getTelefone())));
    }

    @Test
    void testUpdateCliente() throws Exception {
        when(service.update(Mockito.eq(1), any(ClienteDTO.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(cliente.getId())))
                .andExpect(jsonPath("$.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.cpf", is(cliente.getCpf())))
                .andExpect(jsonPath("$.telefone", is(cliente.getTelefone())));
    }

    @Test
    void testDeleteCliente() throws Exception {
        doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

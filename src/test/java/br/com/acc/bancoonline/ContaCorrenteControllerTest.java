package br.com.acc.bancoonline;
import br.com.acc.bancoonline.controller.ContaCorrenteController;
import br.com.acc.bancoonline.dto.ContaCorrenteDTO;
import br.com.acc.bancoonline.exceptions.*;
import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.service.ContaCorrenteService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContaCorrenteController.class)
public class ContaCorrenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaCorrenteService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ContaCorrenteDTO contaCorrenteDTO;
    private ContaCorrente contaCorrente;

    @BeforeEach
    void setUp() {
        contaCorrenteDTO = new ContaCorrenteDTO();
        contaCorrenteDTO.setAgencia("0001");
        contaCorrenteDTO.setNumero("123456");
        contaCorrenteDTO.setSaldo(1000.0);
        contaCorrenteDTO.setIdCliente(1); // Supondo que o cliente ID 1 existe

        contaCorrente = new ContaCorrente();
        contaCorrente.setId(1);
        contaCorrente.setAgencia("0001");
        contaCorrente.setNumero("123456");
        contaCorrente.setSaldo(1000.0);
    }

    @Test
    void testCreateContaCorrente() throws Exception {
        doNothing().when(service).create(any(ContaCorrenteDTO.class));

        mockMvc.perform(post("/api/contaCorrentes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaCorrenteDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testFindById() throws Exception {
        when(service.findById(1)).thenReturn(contaCorrente);

        mockMvc.perform(get("/api/contaCorrentes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contaCorrente.getId())))
                .andExpect(jsonPath("$.agencia", is(contaCorrente.getAgencia())))
                .andExpect(jsonPath("$.numero", is(contaCorrente.getNumero())))
                .andExpect(jsonPath("$.saldo", is(contaCorrente.getSaldo())));
    }

    @Test
    void testFindAllContasCorrentes() throws Exception {
        List<ContaCorrente> contasCorrentes = Arrays.asList(contaCorrente);
        when(service.findAll()).thenReturn(contasCorrentes);

        mockMvc.perform(get("/api/contaCorrentes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(contaCorrente.getId())))
                .andExpect(jsonPath("$[0].agencia", is(contaCorrente.getAgencia())))
                .andExpect(jsonPath("$[0].numero", is(contaCorrente.getNumero())))
                .andExpect(jsonPath("$[0].saldo", is(contaCorrente.getSaldo())));
    }

    @Test
    void testUpdateContaCorrente() throws Exception {
        when(service.update(Mockito.eq(1), any(ContaCorrenteDTO.class))).thenReturn(contaCorrente);

        mockMvc.perform(put("/api/contaCorrentes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contaCorrenteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contaCorrente.getId())))
                .andExpect(jsonPath("$.agencia", is(contaCorrente.getAgencia())))
                .andExpect(jsonPath("$.numero", is(contaCorrente.getNumero())))
                .andExpect(jsonPath("$.saldo", is(contaCorrente.getSaldo())));
    }

    @Test
    void testDeleteContaCorrente() throws Exception {
        doNothing().when(service).deleteById(1);

        mockMvc.perform(delete("/api/contaCorrentes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDepositar() throws Exception {
        when(service.depositar(1, 500.0)).thenReturn(1500.0);

        mockMvc.perform(patch("/api/contaCorrentes/depositar/1/500")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1500.0)));
    }

    @Test
    void testSacar() throws Exception {
        when(service.sacar(1, 500.0)).thenReturn(500.0);

        mockMvc.perform(patch("/api/contaCorrentes/sacar/1/500")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(500.0)));
    }

    @Test
    void testTransferir() throws Exception {
        when(service.transferir(500.0, 1, "12345678909")).thenReturn(500.0);

        mockMvc.perform(patch("/api/contaCorrentes/transferir/500/1/12345678909")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(500.0)));
    }

    @Test
    void testConsultarDadosDaConta() throws Exception {
        when(service.getContaByCpf("12345678909")).thenReturn(contaCorrente);

        mockMvc.perform(get("/api/contaCorrentes/consultarDadosDaConta/12345678909")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contaCorrente.getId())))
                .andExpect(jsonPath("$.agencia", is(contaCorrente.getAgencia())))
                .andExpect(jsonPath("$.numero", is(contaCorrente.getNumero())))
                .andExpect(jsonPath("$.saldo", is(contaCorrente.getSaldo())));
    }
}

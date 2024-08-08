package br.com.acc.bancoonline.service;

import br.com.acc.bancoonline.dto.AgenciaDTO;
import br.com.acc.bancoonline.exceptions.AgenciaNaoEncontradaException;
import br.com.acc.bancoonline.exceptions.CampoVazioGenericoException;
import br.com.acc.bancoonline.exceptions.ClienteNaoEncontradoException;
import br.com.acc.bancoonline.model.Agencia;
import br.com.acc.bancoonline.repository.AgenciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AgenciaService {
    private final AgenciaRepository repository;

    private final ClienteService clienteService;

    public void create(AgenciaDTO agenciaDTO) throws CampoVazioGenericoException, ClienteNaoEncontradoException {
        if(Objects.isNull(agenciaDTO.getNomeAgencia()) || Objects.isNull(agenciaDTO.getEndereco()) || Objects.isNull(agenciaDTO.getTelefone())) {
            throw new CampoVazioGenericoException();
        }

        Agencia agencia = new Agencia();
        agencia.setTelefone(agenciaDTO.getTelefone());
        agencia.setNomeAgencia(agenciaDTO.getNomeAgencia());
        agencia.setEndereco(agenciaDTO.getEndereco());
        agencia.setCliente(clienteService.findById(agenciaDTO.getIdCliente()));

        repository.save(agencia);
    }

    public Agencia findById(int id) throws AgenciaNaoEncontradaException {
        if (repository.findById(id).isEmpty()) {
            throw new AgenciaNaoEncontradaException();
        }
        return repository.findById(id).get();
    }

    public List<Agencia> findAll() {
        return repository.findAll();
    }

    public Agencia update(int id, AgenciaDTO newAgenciaDTO) throws AgenciaNaoEncontradaException, CampoVazioGenericoException, ClienteNaoEncontradoException {
        Agencia agencia = this.findById(id);
        if(Objects.isNull(newAgenciaDTO.getNomeAgencia()) || Objects.isNull(newAgenciaDTO.getEndereco()) || Objects.isNull(newAgenciaDTO.getTelefone())) {
            throw new CampoVazioGenericoException();
        }

        agencia.setNomeAgencia(newAgenciaDTO.getNomeAgencia());
        agencia.setEndereco(newAgenciaDTO.getEndereco());
        agencia.setTelefone(newAgenciaDTO.getTelefone());
        agencia.setCliente(clienteService.findById(newAgenciaDTO.getIdCliente()));
        repository.save(agencia);
        return agencia;
    }

    public void deleteById(int id) throws AgenciaNaoEncontradaException {
        Agencia agencia = this.findById(id);
        repository.delete(agencia);
    }
}

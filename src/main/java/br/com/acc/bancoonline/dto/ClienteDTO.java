package br.com.acc.bancoonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private String nome;

    private String cpf;

    private String telefone;

    private List<Integer> agenciaIds = new ArrayList<>();

    private List<Integer> contaCorrenteIds = new ArrayList<>();
}

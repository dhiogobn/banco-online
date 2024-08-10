package br.com.acc.bancoonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenciaDTO {

    private String nomeAgencia;
    private String endereco;
    private String telefone;

}

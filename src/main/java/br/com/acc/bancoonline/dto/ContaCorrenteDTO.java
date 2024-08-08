package br.com.acc.bancoonline.dto;


import br.com.acc.bancoonline.model.Extrato;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaCorrenteDTO {
    private String agencia;

    private String numero;

    private double saldo;

    private int idCliente;
}

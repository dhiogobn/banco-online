package br.com.acc.bancoonline.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {

    private LocalDateTime dataHoraMovimento;

    private String operacao;

    private double valorOperacao;

    private int idContaCorrente;
}

package br.com.acc.bancoonline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Extrato {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private LocalDateTime dataHoraMovimento;
    @Column(length = 12, nullable = false)
    private String operacao;
    @Column
    private double valorOperacao;

    @ManyToOne(cascade = CascadeType.ALL)
    ContaCorrente contaCorrente;

}

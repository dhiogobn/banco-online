package br.com.acc.bancoonline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContaCorrente {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 3, nullable = false)
    private String agencia;

    @Column(length = 45, nullable = false)
    private String numero;

    @Column
    private double saldo;

    @ManyToOne
    private Cliente cliente;
}

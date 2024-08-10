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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 45, nullable = false)
    private String nome;

    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(length = 20, nullable = false)
    private String telefone;
}


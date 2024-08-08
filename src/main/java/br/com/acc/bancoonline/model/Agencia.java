package br.com.acc.bancoonline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 45, nullable = false)
    private String nomeAgencia;
    @Column(length = 45, nullable = false)
    private String endereco;
    @Column(length = 15, nullable = false)
    private String telefone;

    @ManyToOne
    private Cliente cliente;
}

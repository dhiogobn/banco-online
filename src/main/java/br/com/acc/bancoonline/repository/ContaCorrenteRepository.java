package br.com.acc.bancoonline.repository;

import br.com.acc.bancoonline.model.Cliente;
import br.com.acc.bancoonline.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {

    Optional<ContaCorrente> findByClienteCpf(String cpf);
}

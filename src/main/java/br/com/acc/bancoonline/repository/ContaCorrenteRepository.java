package br.com.acc.bancoonline.repository;

import br.com.acc.bancoonline.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {
}

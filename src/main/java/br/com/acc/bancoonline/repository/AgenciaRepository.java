package br.com.acc.bancoonline.repository;

import br.com.acc.bancoonline.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Integer> {
}

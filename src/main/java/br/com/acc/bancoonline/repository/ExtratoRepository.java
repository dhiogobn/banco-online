package br.com.acc.bancoonline.repository;

import br.com.acc.bancoonline.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Integer> {
}

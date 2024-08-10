package br.com.acc.bancoonline.repository;

import br.com.acc.bancoonline.model.ContaCorrente;
import br.com.acc.bancoonline.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Integer> {

    Optional<List<Extrato>> findByContaCorrenteId(int id);


}

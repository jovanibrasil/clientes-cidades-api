package br.com.compasso.clientes.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.clientes.dominio.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
	Optional<Estado> findBySigla(String sigla);
}

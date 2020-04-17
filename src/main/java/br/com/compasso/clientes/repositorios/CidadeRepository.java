package br.com.compasso.clientes.repositorios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.clientes.modelos.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	/**
	 * Faz a busca de uma cidade pelo nome.
	 * @param name
	 * @return
	 */
	Optional<Cidade> findByNome(String name);
	/**
	 * Busca cidades de um determinado estado.
	 * 
	 * @param sigla
	 * @return
	 */
	Page<Cidade> findByEstadoSigla(String sigla, Pageable pageable);
	
}

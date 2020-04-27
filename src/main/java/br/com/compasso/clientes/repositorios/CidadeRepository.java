package br.com.compasso.clientes.repositorios;

import java.util.Optional;

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
	Optional<Cidade> findByNomeIgnoreCase(String name);
	/**
	 * Busca cidade de um determinado estado.
	 * 
	 * @param nome é o nome da cidade que se busca
	 * @param sigla é a cigra que identifica o estado da cidade que se está buscando
	 * @return
	 */
	Optional<Cidade> findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(String nome, String sigla);
	
}

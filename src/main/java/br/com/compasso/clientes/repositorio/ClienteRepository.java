package br.com.compasso.clientes.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.clientes.dominio.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByNomeCompletoContainingIgnoreCase(String nome);
}

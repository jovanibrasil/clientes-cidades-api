package br.com.compasso.clientes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.compasso.clientes.modelos.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}

package br.com.compasso.clientes.services;

import java.util.List;

import br.com.compasso.clientes.modelos.Cliente;

public interface ClienteService {

	Cliente salvaCliente(Cliente cliente);
	Cliente buscaPorId(Long clienteId);
	List<Cliente> buscaPorNome(String nome);
	
}

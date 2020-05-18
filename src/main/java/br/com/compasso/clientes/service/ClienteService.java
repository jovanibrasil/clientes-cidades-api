package br.com.compasso.clientes.service;

import java.util.List;

import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;

public interface ClienteService {

	ClienteDTO salvaCliente(ClienteForm clienteForm);
	ClienteDTO buscaPorId(Long clienteId);
	List<ClienteDTO> buscaPorNome(String nome);
	ClienteDTO alteraCliente(AtualizacaoClienteForm cliente);
	void removeCliente(Long clienteId);
	
}

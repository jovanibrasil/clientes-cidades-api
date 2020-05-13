package br.com.compasso.clientes.services;

import java.util.List;

import br.com.compasso.clientes.modelos.dtos.ClienteDTO;
import br.com.compasso.clientes.modelos.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.modelos.forms.ClienteForm;

public interface ClienteService {

	ClienteDTO salvaCliente(ClienteForm clienteForm);
	ClienteDTO buscaPorId(Long clienteId);
	List<ClienteDTO> buscaPorNome(String nome);
	ClienteDTO alteraCliente(AtualizacaoClienteForm cliente);
	void removeCliente(Long clienteId);
	
}

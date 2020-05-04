package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.dtos.ClienteDto;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cliente;

public interface ClienteMapper {
	Cliente clienteFormToCliente(ClienteForm clienteForm);
	Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm);
	ClienteDto clienteToClienteDto(Cliente cliente);
}

package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.dtos.ClienteDTO;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cliente;

public interface ClienteMapper {
	Cliente clienteFormToCliente(ClienteForm clienteForm);
	Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm);
	ClienteDTO clienteToClienteDto(Cliente cliente);
}

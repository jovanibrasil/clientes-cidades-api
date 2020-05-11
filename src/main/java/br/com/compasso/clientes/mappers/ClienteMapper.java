package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.dtos.ClienteDTO;
import br.com.compasso.clientes.modelos.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.modelos.forms.ClienteForm;

public interface ClienteMapper {
	Cliente clienteFormToCliente(ClienteForm clienteForm);
	Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm);
	ClienteDTO clienteToClienteDto(Cliente cliente);
}

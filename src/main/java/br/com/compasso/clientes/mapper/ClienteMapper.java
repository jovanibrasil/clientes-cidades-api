package br.com.compasso.clientes.mapper;

import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;

public interface ClienteMapper {
	Cliente clienteFormToCliente(ClienteForm clienteForm);
	Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm);
	ClienteDTO clienteToClienteDto(Cliente cliente);
}

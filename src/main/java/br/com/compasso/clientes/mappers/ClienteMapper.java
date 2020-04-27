package br.com.compasso.clientes.mappers;

import org.mapstruct.Mapper;

import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cliente;

@Mapper
public interface ClienteMapper {

	Cliente clienteFormToCliente(ClienteForm clienteForm);
	
}

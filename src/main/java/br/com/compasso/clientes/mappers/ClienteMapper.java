package br.com.compasso.clientes.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.compasso.clientes.dtos.ClienteDto;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cliente;

@Mapper
@DecoratedWith(ClienteMapperDecorator.class)
public interface ClienteMapper {

	Cliente clienteFormToCliente(ClienteForm clienteForm);
	Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm);
	@Mapping(source = "cidade.id", target = "idCidade")
	ClienteDto clienteToClienteDto(Cliente cliente);
	
}

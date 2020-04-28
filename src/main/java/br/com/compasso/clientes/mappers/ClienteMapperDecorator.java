package br.com.compasso.clientes.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.services.CidadeService;

public abstract class ClienteMapperDecorator implements ClienteMapper {

	private ClienteMapper clientMapper;
	private CidadeService cidadeService;
	
	@Override
	public Cliente clienteFormToCliente(ClienteForm clienteForm) {
		Cliente cliente = clientMapper.clienteFormToCliente(clienteForm);
		Cidade cidade = cidadeService.buscaPorId(clienteForm.getCidadeId());
		cliente.setCidade(cidade);
		return cliente;
	}
	
	@Autowired
	public void setClientMapper(ClienteMapper clientMapper) {
		this.clientMapper = clientMapper;
	}
	
	@Autowired
	public void setCidadeService(CidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}
	
}

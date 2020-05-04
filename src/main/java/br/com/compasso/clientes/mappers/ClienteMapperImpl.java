package br.com.compasso.clientes.mappers;

import org.springframework.stereotype.Component;

import br.com.compasso.clientes.dtos.ClienteDto;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.services.CidadeService;

@Component
public class ClienteMapperImpl implements ClienteMapper {

	private CidadeService cidadeService;
	
	public ClienteMapperImpl(CidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}
	
	@Override
	public Cliente clienteFormToCliente(ClienteForm clienteForm) {
		if (clienteForm == null) {
			return null;
		}

		Cliente cliente = new Cliente();

		cliente.setNomeCompleto(clienteForm.getNomeCompleto());
		cliente.setDataNascimento(clienteForm.getDataNascimento());
		cliente.setSexo(clienteForm.getSexo());

		Cidade cidade = cidadeService.buscaPorId(clienteForm.getCidadeId());
		cliente.setCidade(cidade);
		
		return cliente;
	}

	@Override
	public Cliente atualizacaoClienteFormToCliente(AtualizacaoClienteForm atualizacaoClienteForm) {
		if (atualizacaoClienteForm == null) {
			return null;
		}

		Cliente cliente = new Cliente();

		cliente.setNomeCompleto(atualizacaoClienteForm.getNomeCompleto());

		return cliente;
	}

	@Override
	public ClienteDto clienteToClienteDto(Cliente cliente) {
		if (cliente == null) {
			return null;
		}

		ClienteDto clienteDto = new ClienteDto();

		clienteDto.setIdCidade(cliente.getCidade().getId());
		clienteDto.setId(cliente.getId());
		clienteDto.setNomeCompleto(cliente.getNomeCompleto());
		clienteDto.setDataNascimento(cliente.getDataNascimento());
		clienteDto.setIdade(cliente.getIdade());
		clienteDto.setSexo(cliente.getSexo());

		return clienteDto;
	}

}

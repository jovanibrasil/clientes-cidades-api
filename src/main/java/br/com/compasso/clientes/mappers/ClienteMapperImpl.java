package br.com.compasso.clientes.mappers;

import org.springframework.stereotype.Component;

import br.com.compasso.clientes.dtos.ClienteDTO;
import br.com.compasso.clientes.exceptions.InvalidParameterException;
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

		try {
			Cidade cidade = cidadeService.buscaPorId(clienteForm.getCidadeId());
			cliente.setCidade(cidade);
			return cliente;
		} catch (Exception e) {
			throw new InvalidParameterException("Id da cidade é inválido.");
		}
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
	public ClienteDTO clienteToClienteDto(Cliente cliente) {
		if (cliente == null) {
			return null;
		}

		ClienteDTO clienteDto = new ClienteDTO();

		clienteDto.setIdCidade(cliente.getCidade().getId());
		clienteDto.setId(cliente.getId());
		clienteDto.setNomeCompleto(cliente.getNomeCompleto());
		clienteDto.setDataNascimento(cliente.getDataNascimento());
		clienteDto.setIdade(cliente.getIdade());
		clienteDto.setSexo(cliente.getSexo());

		return clienteDto;
	}

}

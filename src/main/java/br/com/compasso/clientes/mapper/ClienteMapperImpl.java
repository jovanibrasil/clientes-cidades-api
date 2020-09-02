package br.com.compasso.clientes.mapper;

import org.springframework.stereotype.Component;

import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.enumeration.Sexo;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.service.CidadeService;
import br.com.compasso.clientes.util.DateUtils;

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
		cliente.setDataNascimento(DateUtils.converteStringToLocalDate(clienteForm.getDataNascimento()));
		cliente.setSexo(Sexo.valueOf(clienteForm.getSexo()));

		try {
			Cidade cidade = cidadeService.buscaPorId(Long.valueOf(clienteForm.getCidadeId()));
			cliente.setCidade(cidade);
			return cliente;
		} catch (Exception e) {
			throw new InvalidParameterException("Id da cidade é inválido. Não existe cidade com o Id informado");
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

package br.com.compasso.clientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.mapper.ClienteMapper;
import br.com.compasso.clientes.repositorio.ClienteRepository;
import br.com.compasso.clientes.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	private final ClienteMapper clienteMapper;
	
	public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
	}

	/**
	 * Salva um cliente no sistema.
	 * 
	 */
	@Override
	public ClienteDTO salvaCliente(ClienteForm clienteForm) {
		Cliente cliente = clienteMapper.clienteFormToCliente(clienteForm);
		return clienteMapper.clienteToClienteDto(clienteRepository.save(cliente));
	}

	/**
	 * Busca um cliente pelo ID.
	 * 
	 */
	@Override
	public ClienteDTO buscaPorId(Long clienteId) throws NotFoundException {
		return clienteRepository.findById(clienteId)
			.map(clienteMapper::clienteToClienteDto)
			.orElseThrow(() -> new NotFoundException("Cliente com id=" + clienteId + " não foi encontrado"));
	}

	/**
	 * Busca cliente pelo nome. Se existir mais de um cliente com o mesmo nome, ambos
	 * os clientes são retornados. A busca é feita considerando uma comparação parcial
	 * entre a string do nome buscado e os nomes existentes.
	 * 
	 */
	@Override
	public List<ClienteDTO> buscaPorNome(String nome) {
		return clienteRepository.findByNomeCompletoContainingIgnoreCase(nome).stream()
				.map(clienteMapper::clienteToClienteDto)
				.collect(Collectors.toList());
	}

	/**
	 * Faz a alteração do cliente. No momento é possível modificar apenas o nome do cliente.
	 */
	@Transactional
	@Override
	public ClienteDTO alteraCliente(AtualizacaoClienteForm atualizaClienteForm) {
		return clienteRepository.findById(atualizaClienteForm.getId())
			.map(clienteSalvo -> {
				clienteSalvo.setNomeCompleto(atualizaClienteForm.getNomeCompleto());		
				return clienteMapper.clienteToClienteDto(clienteRepository.save(clienteSalvo));
			})
			.orElseThrow(() -> new NotFoundException("Cliente com id=" + atualizaClienteForm.getId() + " não foi encontrado"));
	}

	/**
	 * Remove um cliente pelo id específico.
	 * 
	 */
	@Transactional
	@Override
	public void removeCliente(Long clienteId) {
		clienteRepository.findById(clienteId)
			.ifPresentOrElse(
					clienteRepository::delete,
					() -> { 
						throw new NotFoundException("Cliente com id=" + clienteId + " não foi encontrado"); 
					}
			);
	}
	
}

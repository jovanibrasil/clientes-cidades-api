package br.com.compasso.clientes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.repositorios.ClienteRepository;
import br.com.compasso.clientes.services.ClienteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	
	/**
	 * Salva um cliente no sistema.
	 * 
	 */
	@Override
	public Cliente salvaCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	/**
	 * Busca um cliente pelo ID.
	 * 
	 */
	@Override
	public Cliente buscaPorId(Long clienteId) {
		Optional<Cliente> optCliente = clienteRepository.findById(clienteId);
		
		if(optCliente.isEmpty()) {
			throw new NotFoundException("Cliente não encontrado");
		}
		
		return optCliente.get();
	}

	/**
	 * Busca cliente pelo nome. Se existir mais de um cliente com o mesmo nome, ambos
	 * os clientes são retornados. A busca é feita considerando uma comparação parcial
	 * entre a string do nome buscado e os nomes existentes.
	 * 
	 */
	@Override
	public List<Cliente> buscaPorNome(String nome) {
		List<Cliente> clientes = clienteRepository.findByNomeCompletoContainingIgnoreCase(nome);
		if(clientes.size() == 0) {
			throw new NotFoundException("Nenhum cliente encontrado com o nome informado");
		}
		return clientes;
	}
	
}
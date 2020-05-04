package br.com.compasso.clientes.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.repositorios.ClienteRepository;
import br.com.compasso.clientes.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	
	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

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

	/**
	 * Faz a alteração do cliente. No momento é possível modificar apenas o nome do cliente.
	 */
	@Transactional
	@Override
	public Cliente alteraCliente(Cliente cliente) {
		Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());
		
		if(optCliente.isEmpty()) {
			throw new NotFoundException("Cliente não encontrado");
		}
		
		Cliente clienteSalvo = optCliente.get();
		clienteSalvo.setNomeCompleto(cliente.getNomeCompleto());
		
		return clienteRepository.save(clienteSalvo);
	}

	/**
	 * Remove um cliente pelo id específico.
	 * 
	 */
	@Transactional
	@Override
	public void removeCliente(Long clienteId) {
		Optional<Cliente> optCliente = clienteRepository.findById(clienteId);
		
		if(optCliente.isEmpty()) {
			throw new NotFoundException("Cliente não encontrado");
		}
		
		clienteRepository.delete(optCliente.get());
	}
	
}

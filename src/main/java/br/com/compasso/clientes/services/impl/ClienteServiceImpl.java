package br.com.compasso.clientes.services.impl;

import org.springframework.stereotype.Service;

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
	
}

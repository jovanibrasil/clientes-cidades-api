package br.com.compasso.clientes.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.mappers.ClienteMapper;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.services.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;
	private final ClienteMapper clienteMapper;
	
	/**
	 * Salva um cliente no sistema.
	 * 
	 * @param clienteForm
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> salvaCliente(@RequestBody @Valid ClienteForm clienteForm) {
		Cliente clienteSalvo = clienteService.salvaCliente(clienteMapper.clienteFormToCliente(clienteForm));
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{clienteId}")
				.buildAndExpand(clienteSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
}

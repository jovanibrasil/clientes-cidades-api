package br.com.compasso.clientes.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.dtos.ClienteDto;
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
	
	/**
	 * Busca cliente por ID.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(params = "id")
	public ResponseEntity<ClienteDto> buscaClientePorId(@RequestParam Long id){
		return ResponseEntity.ok(clienteMapper.clienteToClienteDto(clienteService.buscaPorId(id)));
	}
	
	/**
	 * Busca cliente por nome. Como podem haver clientes com o mesmo nome, o retorno é uma lista. 
	 * O matching de comparação é feito de forma parcial. Exemplo: nome buscado é "Jovani". Serão
	 * retornados todos os cliente com nome "Jovani", "Jovanir", "Jovanilson", etc. Para buscar
	 * apenas pelo nome exato basta adicionar um espaço ao fim do nome, por exemplo "Jovani ".
	 * 
	 * A busca é feita no nome completo, então você pode busca pelo nome e sobrenome. Um exemplo
	 * seria a busca por "Jovani Brasil", que retornaria o registro dos clientes que possuem 
	 * nome "Jovani Brasil".
	 * 
	 * @param nome
	 * @return
	 */
	@GetMapping(params = "nome")
	public ResponseEntity<List<ClienteDto>> buscaClientePorNome(@RequestParam String nome){
		return ResponseEntity.ok(clienteService.buscaPorNome(nome)
				.stream()
				.map(cliente -> clienteMapper.clienteToClienteDto(cliente))
				.collect(Collectors.toList()));
	}
	
}

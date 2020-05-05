package br.com.compasso.clientes.controllers;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.dtos.ClienteDto;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.mappers.ClienteMapper;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.services.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	private final ClienteService clienteService;
	private final ClienteMapper clienteMapper;
	
	public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
		this.clienteService = clienteService;
		this.clienteMapper = clienteMapper;
	}

	@ApiOperation(value = "Cria cliente.", notes = "Cria um cliente no sistema. ")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Criado com sucesso.", responseHeaders = { @ResponseHeader(name = "location", response = URI.class)}),
			@ApiResponse(code = 400, message = "Requisição inválida.")})
	@PostMapping
	public ResponseEntity<Void> salvaCliente(@RequestBody @Valid ClienteForm clienteForm) {
		log.info("Criando cliente {}", clienteForm.getNomeCompleto());
		Cliente clienteSalvo = clienteService.salvaCliente(clienteMapper.clienteFormToCliente(clienteForm));
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{clienteId}")
				.buildAndExpand(clienteSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
		
	@ApiOperation(value = "Busca cliente por identificador (ID) ou nome.",
			notes = "Na busca de cliente por nome, Como podem haver clientes com o mesmo nome, o retorno é uma lista. "
					+ "O matching de comparação é feito de forma parcial. Exemplo: nome buscado é \"Jovani\". "
					+ "Serão retornados todos os cliente com nome \"Jovani\", \"Jovanir\", \"Jovanilson\", etc. "
					+ "Para buscar apenas pelo nome exato basta adicionar um espaço ao fim do nome, por exemplo \"Jovani \". "
					+ "A busca é feita no nome completo, então você pode busca pelo nome e sobrenome. "
					+ "Um exemplo seria a busca por \"Jovani Brasil\", que retornaria o registro dos "
					+ "clientes que possuem nome \"Jovani Brasil\".")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente encontrado.", response = Object.class),
		@ApiResponse(code = 404, message = "Cliente não encontrado.")})
	@GetMapping
	public ResponseEntity<?> buscaClientePorNome(@RequestParam(required = false) String nome, 
			@RequestParam(required = false) Long id){
		if(id != null) {
			return ResponseEntity.ok(clienteMapper.clienteToClienteDto(clienteService.buscaPorId(id)));
		} else if (nome != null) {
			return ResponseEntity.ok(clienteService.buscaPorNome(nome)
					.stream()
					.map(cliente -> clienteMapper.clienteToClienteDto(cliente))
					.collect(Collectors.toList()));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@ApiOperation(value = "Remove cliente", notes = "Remove o cliente com Id especificado.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cliente removido com sucesso."),
		@ApiResponse(code = 404, message = "Cliente não encontrado.")})
	@DeleteMapping("/{clienteId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCliente(@PathVariable Long clienteId){
		log.info("Removendo cliente id={}", clienteId);
		clienteService.removeCliente(clienteId);
	}
	
	@ApiOperation(value = "Faz o patch do cliente", notes = "Faz o patch do cliente. Neste momento é apenas permitido o patch do nome do cliente.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente atualizado com sucesso.", response = ClienteDto.class),
		@ApiResponse(code = 404, message = "Cliente não encontrado.")})
	@PatchMapping("/{clienteId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ClienteDto atualizaCliente(@PathVariable Long clienteId, 
			@RequestBody @Valid AtualizacaoClienteForm atualizacaoClienteForm){
		Cliente cliente = clienteMapper.atualizacaoClienteFormToCliente(atualizacaoClienteForm);
		cliente.setId(clienteId);
		log.info("Atualizando cliente id={}", clienteId);
		cliente = clienteService.alteraCliente(cliente);
		return clienteMapper.clienteToClienteDto(cliente);
	}
	
}

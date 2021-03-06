package br.com.compasso.clientes.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@ApiOperation(value = "Cria cliente.", notes = "Cria um cliente no sistema. ")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Criado com sucesso.", response = ClienteDTO.class),
			@ApiResponse(code = 400, message = "Erros ne sintaxe nos dados recebidos."),
			@ApiResponse(code = 422, message = "Não existe cidade com o Id informado.")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO salvaCliente(@RequestBody @Valid ClienteForm clienteForm) {
		log.info("Criando cliente {}", clienteForm.getNomeCompleto());
		return clienteService.salvaCliente(clienteForm);
	}
	
	@ApiOperation(value = "Busca cliente por identificador (ID).",
			notes = "Realiza a busca de um cliente pelo id definido")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente encontrado.", response = ClienteDTO.class),
		@ApiResponse(code = 400, message = "Requisição inválida."),
		@ApiResponse(code = 404, message = "Cliente não encontrado.")})
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ClienteDTO buscaCliente(@PathVariable Long id){
		return clienteService.buscaPorId(id);
	}
	
	@ApiOperation(value = "Busca cliente por nome.",
			notes = "Na busca de cliente por nome, Como podem haver clientes com o mesmo nome, o retorno é uma lista. "
					+ "O matching de comparação é feito de forma parcial. Exemplo: nome buscado é \"Jovani\". "
					+ "Serão retornados todos os cliente com nome \"Jovani\", \"Jovanir\", \"Jovanilson\", etc. "
					+ "A busca é feita no nome completo, então você pode buscar pelo nome e sobrenome. "
					+ "Um exemplo seria a busca por \"Jovani Brasil\", que retornaria o registro dos "
					+ "clientes que possuem nome \"Jovani Brasil\".")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Resultado da busca encontrado.", response = Object.class),
		@ApiResponse(code = 400, message = "Requisição inválida.")})
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<ClienteDTO> buscaCliente(@RequestParam(required = true) String nome){
		return clienteService.buscaPorNome(nome);
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
		@ApiResponse(code = 200, message = "Cliente atualizado com sucesso.", response = ClienteDTO.class),
		@ApiResponse(code = 404, message = "Cliente não encontrado.")})
	@PatchMapping("/{clienteId}")
	@ResponseStatus(value = HttpStatus.OK)
	public ClienteDTO atualizaCliente(@PathVariable Long clienteId, 
			@RequestBody @Valid AtualizacaoClienteForm atualizacaoClienteForm){
		log.info("Atualizando cliente id={}", clienteId);
		atualizacaoClienteForm.setId(clienteId);
		return clienteService.alteraCliente(atualizacaoClienteForm);
	}
	
}

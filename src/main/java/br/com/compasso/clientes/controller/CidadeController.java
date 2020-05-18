package br.com.compasso.clientes.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;
import br.com.compasso.clientes.service.CidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private static final Logger log = LoggerFactory.getLogger(CidadeController.class);
	
	private final CidadeService cidadeService;
	
	public CidadeController(CidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}

	@ApiOperation(value = "Cria uma cidade.", notes = "Cria uma cidade no sistema. Não existem cidades com mesmo nome em um mesmo estado.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada com sucesso.",  responseHeaders = { @ResponseHeader(name = "location", response = URI.class)}),
		@ApiResponse(code = 404, message = "Cidade não encontrada.")})
	@PostMapping
	public ResponseEntity<Void> criaCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		log.info("Criando cidade {} em {}", cidadeForm.getNome(), cidadeForm.getEstadoSigla());
		CidadeDTO cidadeDTO = cidadeService.salvaCidade(cidadeForm);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{nomeCidade}")
				.buildAndExpand(cidadeDTO.getNome())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Busca cidade(s).",	
			notes = "Busca cidade(s) por nome ou estado. Podem várias cidades com mesmo nome, desde que em estados diferentes.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Resultado encontrado.", response = Object.class),
		@ApiResponse(code = 400, message = "Requisição inválida.")})
	@GetMapping
	public ResponseEntity<?> buscaCidade(@RequestParam(required = false) String nome, 
			@RequestParam(required = false) String estadoSigla) {
		// Chama serviço de acordo com o parâmetro passado
		if(nome != null) {
			return ResponseEntity.ok(cidadeService.buscaPorNome(nome));
		} else if(estadoSigla != null) {
			return ResponseEntity.ok(cidadeService.buscaPorEstado(estadoSigla));
		}
		return ResponseEntity.badRequest().build();
	}
	
}

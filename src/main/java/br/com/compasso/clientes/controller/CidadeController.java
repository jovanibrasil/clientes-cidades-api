package br.com.compasso.clientes.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;
import br.com.compasso.clientes.service.CidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
		@ApiResponse(code = 201, message = "Cidade criada com sucesso.", response = CidadeDTO.class),
		@ApiResponse(code = 400, message = "Erros ne sintaxe nos dados recebidos."),
		@ApiResponse(code = 422, message = "Estado informado não foi encontrado.")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO criaCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		log.info("Criando cidade {} em {}", cidadeForm.getNome(), cidadeForm.getEstadoSigla());
		return cidadeService.salvaCidade(cidadeForm);
	}
	
	@ApiOperation(value = "Busca cidade(s).",	
			notes = "Busca cidade(s) por nome ou estado. Podem várias cidades com mesmo nome, desde que em estados diferentes.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Resultado encontrado.", response = Object.class)})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CidadeDTO> buscaCidade(
			@RequestParam(required = false, defaultValue = "") String nome, 
			@RequestParam(required = false, defaultValue = "") String estadoSigla) {
		return cidadeService.buscaPorNomeCidadeESiglaEstado(nome, estadoSigla);
	}
	
}

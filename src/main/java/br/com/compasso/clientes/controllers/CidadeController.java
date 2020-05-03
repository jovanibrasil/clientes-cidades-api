package br.com.compasso.clientes.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.mappers.CidadeMapper;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.services.CidadeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

	private final CidadeService cidadeService;
	private final CidadeMapper cidadeMapper;
	
	
	@ApiOperation(value = "Cria uma cidade.", notes = "Cria uma cidade no sistema. Não existem cidades com mesmo nome em um mesmo estado.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada com sucesso.",  responseHeaders = { @ResponseHeader(name = "location", response = URI.class)}),
		@ApiResponse(code = 404, message = "Cidade não encontrada.")})
	@PostMapping
	public ResponseEntity<Void> criaCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		Cidade cidade = cidadeMapper.cidadeFormToCidade(cidadeForm);
		cidade = cidadeService.salvaCidade(cidade);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{nomeCidade}")
				.buildAndExpand(cidade.getNome())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Busca cidade por nome.",	notes = "Busca cidade por nome. Podem várias cidades com mesmo nome, desde que em estados diferentes.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade encontrada.", response = CidadeDto.class, responseContainer = "List"),
		@ApiResponse(code = 404, message = "Cidade não encontrada.")})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{nomeCidade}")
	public List<CidadeDto> buscaCidadePorNome(@PathVariable String nomeCidade) {
		return cidadeService.buscaPorNome(nomeCidade).stream()
					.map(cidadeMapper::cidadeToCidadeDto)
					.collect(Collectors.toList());
	}
	
	@ApiOperation(value = "Busca cidade por nome e estado.", notes = "Busca por uma determinada cidade em um determinado estado. Uma cidade tem nome único em um estado.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade encontrada.", response = CidadeDto.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada.")})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{nomeCidade}/{estadoSigla}")
	public CidadeDto buscaCidadePorEstado(@PathVariable String estadoSigla, @PathVariable String nomeCidade) {
		return cidadeMapper.cidadeToCidadeDto(cidadeService.buscaPorEstado(nomeCidade, estadoSigla));
	}
	
}

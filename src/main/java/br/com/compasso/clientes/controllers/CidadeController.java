package br.com.compasso.clientes.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.mappers.CidadeMapper;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.services.CidadeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

	private final CidadeService cidadeService;
	private final CidadeMapper cidadeMapper;
		
	/**
	 * Cria uma cidade no sistema. 
	 * 
	 * @param cidadeForm
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> criaCidade(@RequestBody @Valid CidadeForm cidadeForm) {
		Cidade cidade = cidadeMapper.cidadeFormToCidade(cidadeForm);
		cidade = cidadeService.salvaCidade(cidade);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{nomeCidade}")
				.buildAndExpand(cidade.getNome())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Busca uma cidade por nome.
	 * 
	 * @param nomeCidade
	 * @return
	 */
	@GetMapping("/{nomeCidade}")
	public ResponseEntity<CidadeDto> buscaCidadePorNome(@PathVariable String nomeCidade) {
		return ResponseEntity.ok(cidadeMapper
				.cidadeToCidadeDto(cidadeService.buscaPorNome(nomeCidade)));
	}
	
	/**
	 * Busca uma cidade por nome dentro de um estado específico.
	 * 
	 * @param estadoSigla
	 * @param nomeCidade
	 * @return
	 */
	@GetMapping("/{nomeCidade}/{estadoSigla}")
	public ResponseEntity<CidadeDto> buscaCidadePorEstado(@PathVariable String estadoSigla, @PathVariable String nomeCidade) {
		return ResponseEntity.ok(cidadeMapper
				.cidadeToCidadeDto(cidadeService.buscaPorEstado(nomeCidade, estadoSigla)));
	}
	
}

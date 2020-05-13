package br.com.compasso.clientes.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.mappers.CidadeMapper;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.dtos.CidadeDTO;
import br.com.compasso.clientes.modelos.forms.CidadeForm;
import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.services.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService {

	private final CidadeRepository cidadeRepository;
	private final CidadeMapper cidadeMapper;
	
	public CidadeServiceImpl(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
		this.cidadeRepository = cidadeRepository;
		this.cidadeMapper = cidadeMapper;
	}

	/**
	 * Busca cidade por nome. A busca é feita considerando uma comparação
	 * total no nome buscado.
	 * 
	 * @param nome da cidade que se quer buscar
	 */
	@Transactional
	@Override
	public List<CidadeDTO> buscaPorNome(String nomeCidade) {
		return cidadeRepository.findByNomeIgnoreCase(nomeCidade).stream()
				.map(cidadeMapper::cidadeToCidadeDto)
				.collect(Collectors.toList());
	}

	/**
	 * Busca uma cidade pelo ID especificado.
	 * 
	 */
	@Override
	public Cidade buscaPorId(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> 
			new NotFoundException("Cidade com id=" + cidadeId + " não encontrada."));
	}

	@Override
	public List<CidadeDTO> buscaPorEstado(String estadoSigla) {
		return cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla).stream()
				.map(cidadeMapper::cidadeToCidadeDto)
				.collect(Collectors.toList());
	}

	/**
	 * Salva uma cidade no sistema. Não existem duas cidades com
	 * nomes iguais em um mesmo estado.
	 * 
	 */
	@Transactional
	@Override
	public CidadeDTO salvaCidade(CidadeForm cidadeForm) {
		Cidade cidade = cidadeMapper.cidadeFormToCidade(cidadeForm);
		
		cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), cidade.getEstado().getSigla())
			.ifPresent(cliente -> { 
				throw new InvalidParameterException("A cidade " + cidade.getNome() + " já existe neste estado."); 
			});
		return cidadeMapper.cidadeToCidadeDto(cidadeRepository.save(cidade));
	}
	
}

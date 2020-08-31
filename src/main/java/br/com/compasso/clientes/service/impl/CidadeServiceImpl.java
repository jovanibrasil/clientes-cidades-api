package br.com.compasso.clientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.exception.Messages;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.mapper.CidadeMapper;
import br.com.compasso.clientes.repositorio.CidadeRepository;
import br.com.compasso.clientes.service.CidadeService;

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
			new NotFoundException(Messages.CIDADE_NAO_ENCONTRADA + cidadeId));
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
				throw new InvalidParameterException(Messages.CIDADE_JA_CADASTRADA + cidadeForm.getNome()); 
			});
		return cidadeMapper.cidadeToCidadeDto(cidadeRepository.save(cidade));
	}
	
}

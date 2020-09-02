package br.com.compasso.clientes.service.impl;

import java.util.ArrayList;
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
	 * Busca uma cidade pelo ID especificado.
	 * 
	 */
	@Override
	public Cidade buscaPorId(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> 
			new NotFoundException(Messages.CIDADE_NAO_ENCONTRADA + cidadeId));
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

	/**
	 * Busca cidade por nome, por sigla de estado ou por ambos. A busca é feita considerando 
	 * uma comparação total dos nomes buscados.
	 * 
	 * @param nomeCidade nome da cidade que se quer buscar
	 * @param estadoSigla nome no estado que se quer buscar
	 */
	@Override
	public List<CidadeDTO> buscaPorNomeCidadeESiglaEstado(String nomeCidade, String estadoSigla) {
		
		List<Cidade> cidades = new ArrayList<>();
		
		if(!nomeCidade.isEmpty() && !estadoSigla.isEmpty()) {
			cidadeRepository
				.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(nomeCidade, estadoSigla)
				.ifPresent(cidades::add);
		} else {
			if(!nomeCidade.isEmpty()) {
				cidades = cidadeRepository.findByNomeIgnoreCase(nomeCidade);
			} else if(!estadoSigla.isEmpty()) {
				cidades = cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla);
			} 
		}
		
		return cidades.stream()
					.map(cidadeMapper::cidadeToCidadeDto)
					.collect(Collectors.toList());
	}
	
}

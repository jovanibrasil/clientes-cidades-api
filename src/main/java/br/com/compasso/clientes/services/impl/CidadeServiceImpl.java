package br.com.compasso.clientes.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.services.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService {

	private final CidadeRepository cidadeRepository;
	
	public CidadeServiceImpl(CidadeRepository cidadeRepository) {
		this.cidadeRepository = cidadeRepository;
	}

	/**
	 * Salva uma cidade no sistema. Não existem duas cidades com
	 * nomes iguais em um mesmo estado.
	 * 
	 */
	@Transactional
	@Override
	public Cidade salvaCidade(Cidade cidade) {
		Optional<Cidade> optCidade = cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), cidade.getEstado().getSigla());
		
		if(!optCidade.isEmpty()) {
			throw new InvalidParameterException("Esta cidade já existe neste estado.");
		}
		
		return cidadeRepository.save(cidade);
	}

	/**
	 * Busca cidade por nome. A busca é feita considerando uma comparação
	 * total no nome buscado.
	 * 
	 * @param nome da cidade que se quer buscar
	 */
	@Transactional
	@Override
	public Cidade buscaPorNome(String nomeCidade) {
		Optional<Cidade> optCidade = cidadeRepository.findByNomeIgnoreCase(nomeCidade);
		
		if(optCidade.isEmpty()) {
			throw new NotFoundException("Cidade não encontrada.");
		}

		return optCidade.get();
	}

	/**
	 * Busca cidades de um determinado estado. O estado é identificado pela
	 * sua sigla.
	 * 
	 * @param estadoSigla é o estado que se quer buscar as cidades.
	 * 
	 */
	@Override
	public Cidade buscaPorEstado(String nomeCidade, String siglaEstado) {
		Optional<Cidade> optCidade = cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(nomeCidade, siglaEstado);
		
		if(optCidade.isEmpty()) {
			throw new NotFoundException("Cidade não encontrada.");
		}

		return optCidade.get();
	}

}

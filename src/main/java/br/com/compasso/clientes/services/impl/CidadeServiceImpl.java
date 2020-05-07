package br.com.compasso.clientes.services.impl;

import java.util.List;
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
			throw new InvalidParameterException("A cidade " + cidade.getNome() + " já existe neste estado.");
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
	public List<Cidade> buscaPorNome(String nomeCidade) {
		return cidadeRepository.findByNomeIgnoreCase(nomeCidade);
	}

	/**
	 * Busca uma cidade pelo ID especificado.
	 * 
	 */
	@Override
	public Cidade buscaPorId(Long cidadeId) {
		Optional<Cidade> optCidade = cidadeRepository.findById(cidadeId);
		
		if(optCidade.isEmpty()) {
			throw new NotFoundException("Cidade com id=" + cidadeId + " não encontrada.");
		}

		return optCidade.get();
	}

	@Override
	public List<Cidade> buscaPorEstado(String estadoSigla) {
		return cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla);
	}

}

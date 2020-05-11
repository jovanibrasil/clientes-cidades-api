package br.com.compasso.clientes.services.impl;

import java.util.List;

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
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> 
			new NotFoundException("Cidade com id=" + cidadeId + " não encontrada."));
	}

	@Override
	public List<Cidade> buscaPorEstado(String estadoSigla) {
		return cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla);
	}

	/**
	 * Salva uma cidade no sistema. Não existem duas cidades com
	 * nomes iguais em um mesmo estado.
	 * 
	 */
	@Transactional
	@Override
	public Cidade salvaCidade(Cidade cidade) {
		cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), cidade.getEstado().getSigla())
			.ifPresent(cliente -> { 
				throw new InvalidParameterException("A cidade " + cidade.getNome() + " já existe neste estado."); 
			});
		return cidadeRepository.save(cidade);
	}
	
}

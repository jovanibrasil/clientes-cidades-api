package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.services.impl.CidadeServiceImpl;

@ExtendWith(SpringExtension.class)
class CidadeServiceImplTest {
	
	@InjectMocks
	private CidadeServiceImpl cidadeService;
	@MockBean
	private CidadeRepository cidadeRepository;

	private Cidade cidade;
	private Estado estado;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        cidadeService = new CidadeServiceImpl(cidadeRepository);
		estado = new Estado(0L, "RS");
		cidade = new Cidade(0L, "Porto Alegre", estado);
	}

	/**
	 * Testa salvamento de uma cidade em uma estado onde não existe cidade com
	 * o mesmo nome da cidade a ser salva.
	 * 
	 */
	@Test
	void testSalvaCidadeComEstadoValido() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), 
				estado.getSigla())).thenReturn(Optional.empty());
		when(cidadeRepository.save(cidade)).thenReturn(cidade);
		
		Cidade cidadeSalva = cidadeService.salvaCidade(cidade);
		
		assertEquals(cidade.getNome(), cidadeSalva.getNome());
	}

	/**
	 * Testa salvamento de uma cidade em uma estado onde já existe cidade com
	 * o nome da cidade que se quer salvar.
	 * 
	 */
	@Test
	void testSalvaCidadeComEstadoInvalido() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), 
				estado.getSigla())).thenReturn(Optional.of(cidade));
		
		assertThrows(InvalidParameterException.class, () -> {
			cidadeService.salvaCidade(cidade);	
		});
	}
	
	/**
	 * Busca por nome uma cidade existente no sistema.
	 * 
	 */
	@Test
	void testBuscaPorNome() {
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Optional.of(cidade));	
		Cidade cidadeSalva = cidadeService.buscaPorNome(cidade.getNome());
		assertEquals(cidade.getNome(), cidadeSalva.getNome());
	}
	
	/**
	 * Busca por nome uma cidade não existente no sistema.
	 * 
	 */
	@Test
	void testBuscaPorNomeNaoExistente() {
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Optional.empty());	
		assertThrows(NotFoundException.class, () -> {
			cidadeService.buscaPorNome(cidade.getNome());
		});
	}

	/**
	 * Busca por uma cidade existente em um determinado estado.
	 * 
	 */
	@Test
	void testBuscaCidadePorEstado() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), cidade.getEstado().getSigla()))
			.thenReturn(Optional.of(cidade));	
		Cidade cidadeSalva = cidadeService.buscaPorEstado(cidade.getNome(), cidade.getEstado().getSigla());
		assertEquals(cidade.getNome(), cidadeSalva.getNome());
	}
	
	/**
	 * Busca por uma cidade não existente em um determinado estado.
	 * 
	 */
	@Test
	void testBuscaCidadePorEstadoNaoExistente() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), cidade.getEstado().getSigla())).thenReturn(Optional.empty());	
		assertThrows(NotFoundException.class, () -> {
			cidadeService.buscaPorEstado(cidade.getNome(), cidade.getEstado().getSigla());
		});
	}
	
	/**
	 * Busca por id de uma cidade existente no sistema.
	 * 
	 */
	@Test
	void testBuscaPorId() {
		when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.of(cidade));	
		Cidade cidadeSalva = cidadeService.buscaPorId(cidade.getId());
		assertEquals(cidade.getId(), cidadeSalva.getId());
	}
	
	/**
	 * Busca por id de uma cidade não existente no sistema.
	 * 
	 */
	@Test
	void testBuscaPorIdCidadeNaoExistente() {
		when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());	
		assertThrows(NotFoundException.class, () -> {
			cidadeService.buscaPorId(cidade.getId());
		});
	}

}

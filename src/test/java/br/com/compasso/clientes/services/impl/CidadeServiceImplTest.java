package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.services.impl.CidadeServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CidadeServiceImplTest {
	
	@InjectMocks
	private CidadeServiceImpl cidadeService;
	@MockBean
	private CidadeRepository cidadeRepository;

	private Cidade cidade;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        cidadeService = new CidadeServiceImpl(cidadeRepository);
		cidade = ScenarioFactory.criaCidadePoa();
	}

	/**
	 * Testa salvamento de uma cidade em uma estado onde não existe cidade com
	 * o mesmo nome da cidade a ser salva.
	 * 
	 */
	@Test
	void testSalvaCidadeComEstadoValido() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), 
				cidade.getEstado().getSigla())).thenReturn(Optional.empty());
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
				cidade.getEstado().getSigla())).thenReturn(Optional.of(cidade));
		
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
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Arrays.asList(cidade));	
		List<Cidade> cidadeSalva = cidadeService.buscaPorNome(cidade.getNome());
		assertEquals(cidade.getNome(), cidadeSalva.get(0).getNome());
	}
		
	/**
	 * Busca por nome uma cidade não existente no sistema.
	 * 
	 */
	@Test
	void testBuscaPorNomeNaoExistente() {
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorNome(cidade.getNome()).size());
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
	
	/**
	 * Faz uma busca de cidade por estado, sendo que o estado possui uma cidade.
	 */
	@Test
	void testBuscaDeCidadePorEstado() {
		String estadoSigla = cidade.getEstado().getSigla();
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList(cidade));	
		assertEquals(1, cidadeService.buscaPorEstado(estadoSigla).size());
	}
	
	/**
	 * Faz uma busca de cidade por estado que não existe.
	 */
	@Test
	void testBuscaDeCidadePorEstadoQueNaoExiste() {
		String estadoSigla = "??";
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorEstado(estadoSigla).size());
		
	}
	
	/**
	 * Faz uma busca de cidade por estado que não existe.
	 */
	@Test
	void testBuscaDeCidadePorEstadoSemCidade() {
		String estadoSigla = "AM";
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorEstado(estadoSigla).size());
		
	}
	
}

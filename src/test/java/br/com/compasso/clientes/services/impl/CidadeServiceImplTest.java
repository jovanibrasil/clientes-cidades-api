package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.mapper.CidadeMapper;
import br.com.compasso.clientes.repositorio.CidadeRepository;
import br.com.compasso.clientes.service.impl.CidadeServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CidadeServiceImplTest {
	
	@InjectMocks
	private CidadeServiceImpl cidadeService;
	@MockBean
	private CidadeRepository cidadeRepository;
	@MockBean
	private CidadeMapper cidadeMapper;
	
	private Cidade cidade;

	private CidadeForm cidadeForm;
	private CidadeDTO cidadeDto;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        cidadeService = new CidadeServiceImpl(cidadeRepository, cidadeMapper);
        
		cidade = ScenarioFactory.criaCidadePoa();
		cidadeForm = ScenarioFactory.criaCidadeFormPoa();
		cidadeDto = ScenarioFactory.criaCidadeDtoPoa();
		
		when(cidadeMapper.cidadeFormToCidade(any())).thenReturn(cidade);
		when(cidadeMapper.cidadeToCidadeDto(cidade)).thenReturn(cidadeDto);	
	}

	/**
	 * Testa salvamento de uma cidade em uma estado onde não existe cidade com
	 * o mesmo nome da cidade a ser salva.
	 * 
	 */
	@Test
	void salvaCidade_QuandoEstadoValido_EsperaPorCidadeSalvaComIdNaoNulo() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), 
				cidade.getEstado().getSigla())).thenReturn(Optional.empty());
		when(cidadeRepository.save(cidade)).thenReturn(cidade);
		
		CidadeDTO cidadeSalva = cidadeService.salvaCidade(cidadeForm);
		
		assertEquals(cidade.getNome(), cidadeSalva.getNome());
	}

	/**
	 * Testa salvamento de uma cidade em uma estado onde já existe cidade com
	 * o nome da cidade que se quer salvar.
	 * 
	 */
	@Test
	void salvaCidade_QuandoEstadoInvalido_EsperaPorInvalidParameterException() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(), 
				cidade.getEstado().getSigla())).thenReturn(Optional.of(cidade));
		
		assertThrows(InvalidParameterException.class, () -> {
			cidadeService.salvaCidade(cidadeForm);	
		});
	}
	
	/**
	 * Busca por id de uma cidade existente no sistema.
	 * 
	 */
	@Test
	void buscaPorId_QuandoCidadeComIdExiste_EsperaRetornaCidade() {
		when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.of(cidade));	
		Cidade cidadeSalva = cidadeService.buscaPorId(cidade.getId());
		assertEquals(cidade.getId(), cidadeSalva.getId());
	}
	
	/**
	 * Busca por id de uma cidade não existente no sistema.
	 * 
	 */
	@Test
	void buscaPorId_QuandoCidadeComIdNaoExiste_EsperaLancamentoNotFoundException() {
		when(cidadeRepository.findById(cidade.getId())).thenReturn(Optional.empty());	
		Long cidadeId = cidade.getId();
		assertThrows(NotFoundException.class, () -> {
			cidadeService.buscaPorId(cidadeId);
		});
	}
	
	/**
	 * Faz uma busca de cidade por estado, sendo que o estado possui uma cidade.
	 */
	@Test
	void buscaPorEstado_QuandoEstadoPossuiUmaCidade_EsperaListaComUmaCidade() {
		String estadoSigla = cidade.getEstado().getSigla();
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList(cidade));	
		assertEquals(1, cidadeService.buscaPorNomeCidadeESiglaEstado("", estadoSigla).size());
	}
	
	/**
	 * Faz uma busca de cidade por estado que não existe.
	 */
	@Test
	void buscaPorEstado_QuandoEstadoNaoExiste_EsperaListaVazia() {
		String estadoSigla = "??";
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorNomeCidadeESiglaEstado("", estadoSigla).size());
	}
	
	/**
	 * Faz uma busca de cidade que não possui cidades cadastradas.
	 */
	@Test
	void buscaPorEstado_QuandoEstadoNaoTemCidadesCadastradas_EsperaListaVazia() {
		String estadoSigla = "AM";
		when(cidadeRepository.findByEstadoSiglaIgnoreCase(estadoSigla)).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorNomeCidadeESiglaEstado("", estadoSigla).size());	
	}
	
	/**
	 * Busca por nome uma cidade existente no sistema.
	 * 
	 */
	@Test
	void buscaPorNome_QuandoExisteCidadeComNome_EsperaRetornoCidade() {
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Arrays.asList(cidade));	
		List<CidadeDTO> cidadeSalva = cidadeService.buscaPorNomeCidadeESiglaEstado(cidade.getNome(), "");
		assertEquals(cidade.getNome(), cidadeSalva.get(0).getNome());
	}
	
	/**
	 * Busca por nome e sigla do estado e retorno é uma cidade existente.
	 * 
	 */
	@Test
	void buscaPorNome_QuandoExisteCidadeComNomeNoEstado_EsperaRetornoCidade() {
		when(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase(cidade.getNome(),
				cidade.getEstado().getSigla())).thenReturn(Optional.of(cidade));	
		List<CidadeDTO> cidadeSalva = cidadeService
				.buscaPorNomeCidadeESiglaEstado(cidade.getNome(), cidade.getEstado().getSigla());
		assertEquals(cidade.getNome(), cidadeSalva.get(0).getNome());
		assertEquals(cidade.getEstado().getSigla(), cidadeSalva.get(0).getEstadoSigla());
	}
		
	/**
	 * Busca por nome uma cidade não existente no sistema.
	 * 
	 */
	@Test
	void buscaPorNome_QuandoNaoExisteCidadeComNome_EsperaRetornoListaVazia() {
		when(cidadeRepository.findByNomeIgnoreCase(cidade.getNome())).thenReturn(Arrays.asList());	
		assertEquals(0, cidadeService.buscaPorNomeCidadeESiglaEstado(cidade.getNome(), "").size());
	}
	
}

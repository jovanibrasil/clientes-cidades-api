package br.com.compasso.clientes.repositorios;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Estado;
import br.com.compasso.clientes.repositorio.CidadeRepository;
import br.com.compasso.clientes.repositorio.EstadoRepository;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CidadeRepositoryTest {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	private Cidade cidade1;
	private Cidade cidade2;
	private Estado estado;
	
	@BeforeEach
	void setUp() throws Exception {
		estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		cidade1 = ScenarioFactory.criaCidadePoaIdNull();
		cidade1.setEstado(estado);
		cidade2 = ScenarioFactory.criaCidadeSjIdNull();
		cidade2.setEstado(estado);
	}

	/**
	 * Testa se a operação de salvar cidade retorna não nulo.
	 */
	@Test
	void save_QuandoCidadeValida_EsperaRetornoNaoNulo() {
		cidade1 = cidadeRepository.save(cidade1);
		assertNotNull(cidade1);
	}
	
	/**
	 * Testa se a operação de salvar gerou o id para a entidade.
	 */
	@Test
	void save_QuandoCidadeValida_EsperaRetornoCidadeIdNaoNulo() {
		cidade1 = cidadeRepository.save(cidade1);
		assertNotNull(cidade1.getId());
	}
	
	/**
	 * Testa salvar a cidade com nome com mais caracteres que o permitido.
	 */
	@Test
	void save_CidadeComNomeMaiorQueMaximoPossivel_EsperaDataIntegrityViolationException() {
		cidade1.setNome(StringUtils.repeat("*", 40));
		assertThrows(DataIntegrityViolationException.class, () -> {
			cidadeRepository.save(cidade1);
		});
	}
		
	/**
	 * Busca uma cidade existente por id
	 */
	@Test
	void findById_QuandoCidadeExiste_EsperaOptionalNaoVazio() {
		cidade1 = cidadeRepository.save(cidade1);
		assertTrue(cidadeRepository.findById(cidade1.getId()).isPresent());
	}
	
	/**
	 * Busca uma cidade não existente por id
	 */
	@Test
	void findById_QuandoCidadeNaoExiste_EsperaOptionalVazio() {
		assertFalse(cidadeRepository.findById(-1L).isPresent());
	}
	
	/**
	 * Busca uma cidade existente por nome.
	 */
	@Test
	void findByNomeIgnoreCase_QuandoUmaCidadeExiste_EsperaListaComUmaCidade() {
		cidade1 = cidadeRepository.save(cidade1);
		assertEquals(1, cidadeRepository.findByNomeIgnoreCase(cidade1.getNome()).size());
	}
	
	/**
	 * Busca uma cidade não existente por nome.
	 */
	@Test
	void findByNomeIgnoreCase_QuandoNenhumaCidadeExiste_EsperaListaVazia() {
		assertEquals(0, cidadeRepository.findByNomeIgnoreCase("São Paulo").size());
	}
	
	/**
	 * Lista cidades de um estado existente.
	 */
	@Test
	void findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase_QuandoEstadoExiste_EsperaOptionalNaoVazio() {
		cidadeRepository.save(cidade1);
		cidadeRepository.save(cidade2);
		assertTrue(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase("Porto Alegre", "RS").isPresent());
	}
	
	/**
	 * Lista cidades de um estado não existente.
	 */
	@Test
	void findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase_QuandoEstadoNaoExiste_EsperaOptionalVazio() {
		cidadeRepository.save(cidade1);
		cidadeRepository.save(cidade2);
		assertTrue(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase("Sampa", "SP").isEmpty());
	}
	
}

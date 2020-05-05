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

import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;

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
		estado = new Estado();
		estado.setSigla("RS");
		estado = estadoRepository.save(estado);
		cidade1 = new Cidade();
		cidade1.setNome("Porto Alegre");
		cidade1.setEstado(estado);
		cidade2 = new Cidade();
		cidade2.setNome("São Jerônimo");
		cidade2.setEstado(estado);
	}

	/**
	 * Testa se a operação de salvar cidade retorna não nulo.
	 */
	@Test
	void testSalvaCidadeRetornoNaoNulo() {
		cidade1 = cidadeRepository.save(cidade1);
		assertNotNull(cidade1);
	}
	
	/**
	 * Testa se a operação de salvar gerou o id para a entidade.
	 */
	@Test
	void testSalvaCidadeIdNaoNulo() {
		cidade1 = cidadeRepository.save(cidade1);
		assertNotNull(cidade1.getId());
	}
	
	/**
	 * Testa salvar a cidade com nome com mais caracteres que o permitido.
	 */
	@Test
	void testSalvaCidadeComNomeMaiorQueMaximoPossivel() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			cidade1.setNome(StringUtils.repeat("*", 40));
			cidadeRepository.save(cidade1);
		});
	}
		
	/**
	 * Busca uma cidade existente por id
	 */
	@Test
	void testBuscaCidadeExistente() {
		cidade1 = cidadeRepository.save(cidade1);
		assertTrue(cidadeRepository.findById(cidade1.getId()).isPresent());
	}
	
	/**
	 * Busca uma cidade não existente por id
	 */
	@Test
	void testBuscaCidadeNaoExistente() {
		assertFalse(cidadeRepository.findById(-1L).isPresent());
	}
	
	/**
	 * Busca uma cidade existente por nome.
	 */
	@Test
	void testBuscaPorNomeCidadeExistente() {
		cidade1 = cidadeRepository.save(cidade1);
		assertEquals(1, cidadeRepository.findByNomeIgnoreCase(cidade1.getNome()).size());
	}
	
	/**
	 * Busca uma cidade não existente por nome.
	 */
	@Test
	void testBuscaPorNomeCidadeNaoExistente() {
		assertEquals(0, cidadeRepository.findByNomeIgnoreCase("São Paulo").size());
	}
	
	/**
	 * Lista cidades de um estado existente.
	 */
	@Test
	void testBuscaCidadesPorNomeEstadoExistente() {
		cidadeRepository.save(cidade1);
		cidadeRepository.save(cidade2);
		assertTrue(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase("Porto Alegre", "RS").isPresent());
	}
	
	/**
	 * Lista cidades de um estado não existente.
	 */
	@Test
	void testBuscaCidadesPorNomeEstadoNaoExistente() {
		cidadeRepository.save(cidade1);
		cidadeRepository.save(cidade2);
		assertTrue(cidadeRepository.findByNomeIgnoreCaseAndEstadoSiglaIgnoreCase("Sampa", "SP").isEmpty());
	}
	
}

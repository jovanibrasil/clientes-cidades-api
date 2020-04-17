package br.com.compasso.clientes.repositorios;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.modelos.Estado;

/**
 * Testa as operações de salvar e buscar por id um um objeto {link @Estado}. As 
 * demais operações não são testadas uma vez que não são usadas.
 * 
 * @author jovani.brasil
 *
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class EstadoRepositoryTest {

	@Autowired
	private EstadoRepository estadoRepository;
	private Estado estado;
	
	@BeforeEach
	void setUp() throws Exception {
		estado = new Estado();
		estado.setSigla("RS");
	}

	/**
	 * Testa se  a operação de salvar o estado retorna um estado não nulo.
	 */
	@Test
	void testSalvaEstadoRetornoNaoNulo() {
		estado = estadoRepository.save(estado);
		assertNotNull(estado);
	}
	
	/**
	 * Testa se a operação salvar retorna um estado com id não nulo.
	 */
	@Test
	void testSalvaEstadoRetornoComIdValido() {
		estado = estadoRepository.save(estado);
		assertNotNull(estado.getId());
	}
	
	/**
	 * Testa salvar estado com sigla nula.
	 */
	@Test
	void testSalvaEstadoSiglaNula() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			estado.setSigla(null);
			estadoRepository.save(estado);	
		});
	}
	
	/**
	 * Testa salvar estado com sigla com mais caracteres que o permitido.
	 */
	@Test
	void testSalvaEstadoSiglaMaiorQuePermitido() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			estado.setSigla("COMPASSO-STATE");
			estadoRepository.save(estado);	
		});
	}
	
	/**
	 * Testa a operação de buscar por um estado existente.
	 * 
	 */
	@Test
	void testBuscaEstadoExistente() {
		estado = estadoRepository.save(estado);
		assertTrue(estadoRepository.findById(estado.getId()).isPresent());
	}
	
	/**
	 * Testa a operação de buscar por um estado não existente.
	 * 
	 */
	@Test
	void testBuscaEstadoNaoExistente() {
		assertFalse(estadoRepository.findById(-1L).isPresent());
	}
	
}

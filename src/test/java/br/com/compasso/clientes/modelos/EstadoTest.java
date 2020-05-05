package br.com.compasso.clientes.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class EstadoTest {

	private static final long ESTADO_ID = 1L;
	private static final String ESTADO_SIGLA = "RS";
	
	@Test
	void testCriaEstadoValido() {
		Estado estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
		assertEquals(ESTADO_ID, estado.getId());
		assertEquals(ESTADO_SIGLA, estado.getSigla());
	}
	
	@Test
	void testSetIdValido() {
		Estado estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
		long novoId = 2L;
		estado.setId(novoId);
		assertEquals(novoId, estado.getId());
	}
	
	@Test
	void testSetSiglaValida() {
		Estado estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
		String novaSigla = "SP";
		estado.setSigla(novaSigla);
		assertEquals(novaSigla, estado.getSigla());
	}
	
	@Test
	void testCriaEstadoInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Estado(null, null);
		});
	}
	
	@Test
	void testCriaEstadoIdInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Estado(null, ESTADO_SIGLA);
		});
	}
	
	@Test
	void testCriaEstadoSiglaInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Estado(ESTADO_ID, null);
		});
	}
	
	@Test
	void testsetSiglaInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Estado estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
			estado.setSigla(null);
		});
	}
	
	@Test
	void testsetIdInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Estado estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
			estado.setId(null);
		});
	}
	
}

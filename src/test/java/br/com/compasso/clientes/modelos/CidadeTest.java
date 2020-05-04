package br.com.compasso.clientes.modelos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CidadeTest {

	private static final Estado ESTADO = new Estado(1L, "RS");
	private static final long ID_CIDADE = 2L;
	private static final String NOME_CIDADE = "Porto Alegre";
	
	@Test
	void testCriacaoComValoresNulos() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Cidade(null, null, null);
		});
	}
	
	@Test
	void testCriacaoComIdCidadeNula() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Cidade(null, NOME_CIDADE, ESTADO);
		});
	}

	@Test
	void testCriacaoComNomeCidadeNula() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Cidade(ID_CIDADE, null, ESTADO);
		});
	}
	
	@Test
	void testCriacaoComEstadoNulo() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Cidade(ID_CIDADE, NOME_CIDADE, null);
		});
	}
		
	@Test
	void testSetIdCidadeNula() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cidade cidade = new Cidade(ID_CIDADE, NOME_CIDADE, ESTADO);
			cidade.setId(null);
		});
	}

	@Test
	void testSetNomeCidadeNula() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cidade cidade = new Cidade(1L, NOME_CIDADE, ESTADO);
			cidade.setNome(null);
		});
	}
	
	@Test
	void testSetEstadoNulo() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cidade cidade = new Cidade(1L, NOME_CIDADE, ESTADO);
			cidade.setEstado(null);
		});
	}	

	@Test
	void testCriacaoComValoresValidos() {
		Cidade cidade =	new Cidade(ID_CIDADE, NOME_CIDADE, ESTADO);
		assertEquals(ID_CIDADE, cidade.getId());
		assertEquals(NOME_CIDADE, cidade.getNome());
		assertEquals(ESTADO, cidade.getEstado());
	}
	
	@Test
	void testSetIdValido() {
		Cidade cidade =	new Cidade(ID_CIDADE, NOME_CIDADE, ESTADO);
		long novoId = 100L;
		cidade.setId(100L);
		assertEquals(novoId, cidade.getId());
	}
	
	@Test
	void testSetNomeCidadeValido() {
		Cidade cidade =	new Cidade(ID_CIDADE, NOME_CIDADE, ESTADO);
		String novoNomeCidade = "São Jerônimo";
		cidade.setNome(novoNomeCidade);
		assertEquals(novoNomeCidade, cidade.getNome());
	}
	
	@Test
	void testEstadoValido() {
		Cidade cidade =	new Cidade(ID_CIDADE, NOME_CIDADE, ESTADO);
		Estado novoEstado = new Estado(101L, "SP");
		cidade.setEstado(novoEstado);
		assertEquals(novoEstado, cidade.getEstado());
	}
		
}

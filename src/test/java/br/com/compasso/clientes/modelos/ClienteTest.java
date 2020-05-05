package br.com.compasso.clientes.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ClienteTest {

	private static Long CLIENTE_ID = 1L; 
	private static String CLIENTE_NOME = "João Silva";
	private static Sexo CLIENTE_SEXO = Sexo.F;
	private static LocalDate CLIENTE_DATA_NASCIMENTO = LocalDate.of(1992,6, 13);
	private static Cidade CIDADE = new Cidade(3L, "Porto Alegre", 
			new Estado(2L, "RS")); 
	
	@Test
	void testCriacaoClienteValido() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		assertEquals(CLIENTE_ID, cliente.getId());
		assertEquals(CLIENTE_NOME, cliente.getNomeCompleto());
		assertEquals(CLIENTE_SEXO, cliente.getSexo());
		assertEquals(CLIENTE_DATA_NASCIMENTO, cliente.getDataNascimento());
		assertEquals(CIDADE, cliente.getCidade());
	}
	
	@Test
	void testGetIdade() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		int idade = Period.between(CLIENTE_DATA_NASCIMENTO, LocalDate.now()).getYears();
		assertEquals(idade, cliente.getIdade());
	}

	@Test
	void testSetIdClienteValido() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		Long novoId = 100L;
		cliente.setId(novoId);
		assertEquals(novoId, cliente.getId());
	}
	
	@Test
	void testSetNomeClienteValido() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		String novoNome = "Maria Silva";
		cliente.setNomeCompleto(novoNome);
		assertEquals(novoNome, cliente.getNomeCompleto());
	}
	
	@Test
	void testSetSexoClienteValido() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		cliente.setSexo(Sexo.F);
		assertEquals(Sexo.F, cliente.getSexo());
	}
	
	@Test
	void testSetCidadeClienteValido() {
		Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
				CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
		Cidade novaCidade = new Cidade(101L, "São Paulo", 
				new Estado(102L, "SP"));
		cliente.setCidade(novaCidade);
		assertEquals(novaCidade, cliente.getCidade());
	}
	
	@Test
	void testSetIdClienteInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setId(null);
		});
	}
	
	@Test
	void testSetNomeClienteInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setNomeCompleto(null);
		});
	}
	
	@Test
	void testSetSexoClienteInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setSexo(null);
		});
	}
	
	@Test
	void testSetCidadeClienteInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setCidade(null);
		});
	}
	
	@Test
	void testSetDataNascimentoNoFuturo() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setDataNascimento(LocalDate.now().plusYears(2));
		});
	}

	@Test
	void testSetDataNascimentoNula() {
		assertThrows(IllegalArgumentException.class, () -> {
			Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOME, 
					CLIENTE_DATA_NASCIMENTO, CLIENTE_SEXO, CIDADE);
			cliente.setDataNascimento(null);
		});
	}
		
}

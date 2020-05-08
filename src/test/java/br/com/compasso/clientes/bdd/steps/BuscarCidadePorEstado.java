package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.pt.Dado;

public class BuscarCidadePorEstado extends SpringIntegrationTest {

	
	@Dado("que existe ao menos uma cidade no estado {string}")
	public void que_existe_ao_menos_uma_cidade_no_estado(String sigla) {
	    assertTrue(cidadeRepository.findByEstadoSiglaIgnoreCase(sigla).size() > 0);
	}
	
	@Dado("que não existe uma cidade no estado {string}")
	public void que_não_existe_uma_cidade_no_estado(String sigla) {
		assertTrue(cidadeRepository.findByEstadoSiglaIgnoreCase(sigla).size() == 0);
	}
	
}

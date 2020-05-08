package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertTrue;

import io.cucumber.java.pt.Dado;

public class BuscaClientePorNome extends SpringIntegrationTest {

	@Dado("que existe um cliente cadastrado chamado {string}")
	public void que_existe_um_cliente_cadastrado_chamado(String name) {
	    assertTrue(clienteRepository.findByNomeCompletoContainingIgnoreCase(name).size() > 0);
	}
	
	@Dado("que não existe um cliente chamado {string} cadastrado")
	public void que_não_existe_um_cliente_chamado_cadastrado(String nome) {
	    assertTrue(clienteRepository.findByNomeCompletoContainingIgnoreCase(nome).size() == 0);	    
	}
	
}

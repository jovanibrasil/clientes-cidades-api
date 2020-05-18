package br.com.compasso.clientes.bdd.steps;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Estado;
import io.cucumber.java.pt.Dado;

public class BuscaClientePorNome extends SpringIntegrationTest {

	@Dado("que existe um cliente cadastrado chamado {string}")
	public void que_existe_um_cliente_cadastrado_chamado(String name) {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(ScenarioFactory.criaCidadePoaIdNull(estado));
		clienteRepository.save(ScenarioFactory.criaCliente(name, cidade));
	}
	
	@Dado("que não existe um cliente chamado {string} cadastrado")
	public void que_não_existe_um_cliente_chamado_cadastrado(String nome) {
	    limpaBancoDeDados();	    
	}
	
}

package br.com.compasso.clientes.bdd.steps;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import io.cucumber.java.pt.Dado;

public class BuscarCidadePorEstado extends SpringIntegrationTest {

	
	@Dado("que existe ao menos uma cidade no estado {string}")
	public void que_existe_ao_menos_uma_cidade_no_estado(String sigla) {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(ScenarioFactory.criaCidadePoaIdNull(estado));
		estadoCompartilhado.setObject(cidade);
	}
	
	@Dado("que não existe uma cidade no estado {string}")
	public void que_não_existe_uma_cidade_no_estado(String sigla) {
		limpaBancoDeDados();
	}
	
}

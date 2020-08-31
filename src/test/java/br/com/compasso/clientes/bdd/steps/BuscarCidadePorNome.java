package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Estado;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class BuscarCidadePorNome extends SpringIntegrationTest {

	@Dado("que existe uma cidade {string} cadastrada")
	public void que_existe_uma_cidade_cadastrada(String nome) {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(new Cidade(null, nome, estado));
		estadoCompartilhado.setObject(cidade);
	}

	@Dado("que não existe uma cidade {string} cadastrada")
	public void que_não_existe_uma_cidade_cadastrada(String nome) {
		limpaBancoDeDados();
	}

	@Quando("é feito um GET para {string} com parâmetro {string} com valor {string}")
	public void é_feito_um_GET_para_com_parâmetro_nome(String url, String nomeParam, String valor) {
		get(String.format("%s?%s=%s", url, nomeParam, valor));
	}

	@Então("o corpo da mensagem contém ao menos um objeto")
	public void o_corpo_da_mensagem_contém_ao_menos_um_objeto() {
		@SuppressWarnings("unchecked")
		List<Object> body = (List<Object>) estadoCompartilhado.getResponse().getBody();
		assertTrue(body.size() > 0);
	}

	@Então("o corpo da mensagem não contém objetos")
	public void o_corpo_da_mensagem_não_contém_objetos() {
		@SuppressWarnings("unchecked")
		List<Object> body = (List<Object>) estadoCompartilhado.getResponse().getBody();
		assertEquals(0, body.size());
	}

}

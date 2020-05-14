package br.com.compasso.clientes.bdd.steps;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;

public class BuscarClientePorId extends SpringIntegrationTest {

	@Dado("que existe um cliente cadastrado no sistema")
	public void que_existe_um_cliente_cadastrado_no_sistema() {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(ScenarioFactory.criaCidadePoaIdNull(estado));
		Cliente cliente = clienteRepository.save(ScenarioFactory.criaClienteJoao(cidade));
		estadoCompartilhado.setObject(cliente);
	}

	@Quando("é feito um GET para {string} com o id do cliente")
	public void é_feito_um_GET_para_com_o_id_do_cliente(String url) {
		get(url + ((Cliente) estadoCompartilhado.getObject()).getId().toString());
	}
	
	@Quando("é feito um GET para {string} com variável {string}")
	public void é_feito_um_GET_para_com_variavel(String url, String valor) {
		get(url + valor);
	}

}

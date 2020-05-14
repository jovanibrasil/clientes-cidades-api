package br.com.compasso.clientes.bdd.steps;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;

public class RemoveCliente extends SpringIntegrationTest {
	
	@Dado("que existe um cliente cadastrado")
	public void que_existe_um_cliente_cadastrado() {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(ScenarioFactory.criaCidadePoaIdNull(estado));
		estadoCompartilhado.setObject(clienteRepository.save(ScenarioFactory.criaClienteJoao(cidade)));
	}
	
	@Quando("é feito um DELETE para {string} passando o id deste cliente")
	public void é_feito_um_DELETE_para_passando_o_id_deste_cliente(String endpoint) {
		delete(endpoint + ((Cliente) estadoCompartilhado.getObject()).getId().toString());
	}

	@Quando("é feito um DELETE para {string} passando o id {int}")
	public void é_feito_um_DELETE_para_passando_o_id(String endpoint, Integer valor) {
		delete(endpoint + valor.toString());
	}

	@Dado("que não existe um cliente com id {int}")
	public void que_não_existe_um_cliente_com_id(Integer int1) {
		limpaBancoDeDados();
	}
	
}

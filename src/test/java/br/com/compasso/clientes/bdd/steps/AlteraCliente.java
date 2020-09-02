package br.com.compasso.clientes.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class AlteraCliente extends SpringIntegrationTest {
		
	@Quando("é feito um PATCH para {string} passando o id desta cidade")
	public void é_feito_um_PATCH_para_passando_o_id_desta_cidade(String endpoint, DataTable dataTable) {
		String url = endpoint + ((Cliente) estadoCompartilhado.getObject()).getId();		
		AtualizacaoClienteForm atualizacaoCLienteForm = new AtualizacaoClienteForm();
		atualizacaoCLienteForm.setNomeCompleto(dataTable.row(1).get(0));
		
		patch(url, atualizacaoCLienteForm);
	}
	
	@Então("o corpo da mensagem não é vazio")
	public void o_corpo_da_mensagem_não_é_vazio() {
		assertNotNull(estadoCompartilhado.getResponse().getBody());
	}

}

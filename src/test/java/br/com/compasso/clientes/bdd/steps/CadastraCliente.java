package br.com.compasso.clientes.bdd.steps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Estado;
import br.com.compasso.clientes.dominio.enumeration.Sexo;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;


public class CadastraCliente extends SpringIntegrationTest {
	
	@Dado("que existe uma cidade cadastrada")
	public void que_existe_uma_cidade_cadastrada() {
		limpaBancoDeDados();
		Estado estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		Cidade cidade = cidadeRepository.save(ScenarioFactory.criaCidadePoaIdNull(estado));
		estadoCompartilhado.setObject(cidade);
	}

	@Quando("é feito um POST para {string} com o cliente no corpo")
	public void é_feito_um_POST_para_com_o_cliente_no_corpo(String endpoint, DataTable dataTable) {
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setNomeCompleto(dataTable.row(1).get(0));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dataTable.row(1).get(1), formatter);
		clienteForm.setDataNascimento(date);
		clienteForm.setSexo(Sexo.valueOf(dataTable.row(1).get(2)));
		clienteForm.setCidadeId(((Cidade) estadoCompartilhado.getObject()).getId());
		
		post(endpoint, clienteForm);
	}
	
	@Dado("que não existe uma cidade com id {int}")
	public void que_não_existe_uma_cidade_com_id(Integer id) {
	    limpaBancoDeDados();
	}

	
}

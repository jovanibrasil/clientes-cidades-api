package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.compasso.clientes.modelos.enums.Sexo;
import br.com.compasso.clientes.modelos.forms.ClienteForm;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;


public class CadastraCliente extends SpringIntegrationTest {
	
	@Dado("que existe uma cidade cadastrada no sistema com id {int}")
	public void que_existe_uma_cidade_cadastrada_no_sistema_com_id(Integer int1) {
		assertTrue(cidadeRepository.findById((long)int1).isPresent());
	}

	@Quando("é feito um POST para {string} com o cliente no corpo")
	public void é_feito_um_POST_para_com_o_cliente_no_corpo(String endpoint, DataTable dataTable) {
		try {
	    	ClienteForm clienteForm = new ClienteForm();
			clienteForm.setNomeCompleto(dataTable.row(1).get(0));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse(dataTable.row(1).get(1), formatter);
			clienteForm.setDataNascimento(date);
			clienteForm.setSexo(Sexo.valueOf(dataTable.row(1).get(2)));
			clienteForm.setCidadeId(Long.valueOf(dataTable.row(1).get(3)));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(asJsonString(clienteForm), headers);
			ResponseEntity<?> response = restTemplate.exchange(DEFAULT_URL + endpoint, 
					HttpMethod.POST, entity, Object.class);
			estadoCompartilhado.setResponse(response);			
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	}
	
	@Dado("que não existe uma cidade com id {int}")
	public void que_não_existe_uma_cidade_com_id(Integer id) {
	    assertTrue(cidadeRepository.findById((long)id).isEmpty());
	}

	
}

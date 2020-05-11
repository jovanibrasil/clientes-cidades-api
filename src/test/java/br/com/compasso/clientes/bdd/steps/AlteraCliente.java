package br.com.compasso.clientes.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.compasso.clientes.modelos.forms.AtualizacaoClienteForm;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class AlteraCliente extends SpringIntegrationTest {
	
	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	
	@Quando("é feito um PATCH para {string} passando o id desta cidade")
	public void é_feito_um_PATCH_para_passando_o_id_desta_cidade(String endpoint, DataTable dataTable) {
		try {
			URI location = estadoCompartilhado.getResponse().getHeaders().getLocation();
			String url = DEFAULT_URL + (location != null ? location.getPath() : endpoint);
			
			AtualizacaoClienteForm atualizacaoCLienteForm = new AtualizacaoClienteForm();
			atualizacaoCLienteForm.setNomeCompleto(dataTable.row(1).get(0));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(asJsonString(atualizacaoCLienteForm), headers);
			ResponseEntity<?> response = restTemplate.exchange(url, 
		    		HttpMethod.PATCH, entity, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpClientErrorException ex) {
			estadoCompartilhado.setResponse(ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
	                .body(ex.getResponseBodyAsString()));
		}
	}
	
	@Então("o corpo da mensagem não é vazio")
	public void o_corpo_da_mensagem_não_é_vazio() {
		assertNotNull(estadoCompartilhado.getResponse().getBody());
	}

}

package br.com.compasso.clientes.bdd.steps;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import io.cucumber.java.pt.Quando;

public class BuscarClientePorId extends SpringIntegrationTest {

	
	@Quando("é feito um GET para {string} com variável {string}")
	public void é_feito_um_GET_para_com_variavel(String url, String valor) {
		try {
			url = DEFAULT_URL + url + valor;
			ResponseEntity<?> response = restTemplate
					.getForEntity(url, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	}

}

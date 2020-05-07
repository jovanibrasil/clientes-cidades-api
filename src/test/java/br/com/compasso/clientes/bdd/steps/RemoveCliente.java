package br.com.compasso.clientes.bdd.steps;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.compasso.clientes.repositorios.ClienteRepository;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;

public class RemoveCliente extends SpringIntegrationTest {

	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Quando("é feito um DELETE para {string} passando o id desta cidade")
	public void é_feito_um_DELETE_para_passando_o_id_desta_cidade(String endpoint) {
		try {
			URI location = estadoCompartilhado.getResponse().getHeaders().getLocation();
			String url = DEFAULT_URL + (location != null ? location.getPath() : endpoint);
			ResponseEntity<?> response = restTemplate.exchange(url, 
		    		HttpMethod.DELETE, null, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpClientErrorException ex) {
			estadoCompartilhado.setResponse(ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
	                .body(ex.getResponseBodyAsString()));
		}
	}

	@Dado("que não existe um cliente com id {int}")
	public void que_não_existe_um_cliente_com_id(Integer int1) {
		if(clienteRepository.findById((long) int1).isPresent()) {
			clienteRepository.deleteById((long)int1);
		}
	}

}

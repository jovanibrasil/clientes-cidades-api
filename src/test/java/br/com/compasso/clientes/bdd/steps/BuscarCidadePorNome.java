package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.compasso.clientes.modelos.Cidade;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class BuscarCidadePorNome extends SpringIntegrationTest {

	
	@Quando("é feito um GET para {string} com parâmetro {string} com valor {string}")
	public void é_feito_um_GET_para_com_parâmetro_nome(String url, String nomeParam, String valor) {
		try {
			url = String.format("%s%s?%s=%s", DEFAULT_URL, url, nomeParam, valor);
			ResponseEntity<?> response = restTemplate
					.getForEntity(url, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	}

	@Então("o corpo da mensagem contém ao menos um objeto")
	public void o_corpo_da_mensagem_contém_ao_menos_um_objeto() {
	   @SuppressWarnings("unchecked")
	   List<Object> body = (List<Object>) estadoCompartilhado
			   .getResponse()
			   .getBody();
	   assertTrue(body.size() > 0);
	}

	@Então("o corpo da mensagem não contém objetos")
	public void o_corpo_da_mensagem_não_contém_objetos() {
		@SuppressWarnings("unchecked")
		   List<Object> body = (List<Object>) estadoCompartilhado
				   .getResponse()
				   .getBody();
		   assertTrue(body.size() == 0);
	}
	
	@Dado("que existe uma cidade {string} cadastrada")
	public void que_existe_uma_cidade_cadastrada(String nome) {
		List<Cidade> cidades = cidadeRepository
				.findByNomeIgnoreCase(nome); 
		assertTrue(cidades.size() > 0);
	}
	
	@Dado("que não existe uma cidade {string} cadastrada")
	public void que_não_existe_uma_cidade_cadastrada(String nome) {
		List<Cidade> cidades = cidadeRepository
				.findByNomeIgnoreCase(nome); 
		assertEquals(0, cidades.size());
	}

}

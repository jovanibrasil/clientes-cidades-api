package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.compasso.clientes.forms.CidadeForm;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class CadastraCidade extends SpringIntegrationTest {

	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	private String endpoint = "/cidades";
	
	@Quando("o cliente faz um POST para \\/cidades com a cidade no corpo da mensagem")
	public void o_cliente_faz_um_POST_para_cidades_com_a_cidade_no_corpo_da_mensagem() {
		CidadeForm cidadeForm = new CidadeForm();
		cidadeForm.setNome("Porto Alegre");
		cidadeForm.setEstadoSigla("RS");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(asJsonString(cidadeForm), headers);
		ResponseEntity<?> response = restTemplate.exchange(DEFAULT_URL + endpoint, 
				HttpMethod.POST, entity, Void.class);
		estadoCompartilhado.setResponse(response);
	}
	
	@Então("o cliente recebe código de retorno {int}")
	public void o_cliente_recebe_codigo_de_retorno(Integer int1) {
	    assertEquals(int1, estadoCompartilhado.getResponse().getStatusCodeValue());
	}

	@E("o corpo da mensagem é vazio")
	public void o_corpo_da_mensagem_é_vazio() {
	    // Write code here that turns the phrase above into concrete actions
	    assertFalse(estadoCompartilhado.getResponse().hasBody());
	}
	
	@E("possui o endereço do recurso no cabeçalho")
	public void possui_o_endereço_do_recurso_no_cabecalho() {
		assertNotNull(estadoCompartilhado.getResponse().getHeaders().getLocation());
	}
	
}

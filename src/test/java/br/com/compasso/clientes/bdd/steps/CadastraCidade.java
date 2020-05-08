package br.com.compasso.clientes.bdd.steps;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.repositorios.EstadoRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class CadastraCidade extends SpringIntegrationTest {

	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	@Autowired
	private EstadoRepository estadoRepository;
	
	/**
	 * Cenário 1
	 */
	@Dado("que existe um estado {string} pré-cadastrado sem cidades")
	public void que_existe_um_estado_pré_cadastrado_sem_cidades(String sigla) {
		 assertTrue(estadoRepository.findBySigla(sigla).isPresent());
	}

	@Quando("é feito um POST para {string} com a cidade no corpo")
	public void é_feito_um_POST_para_com_a_cidade_no_corpo(String url, DataTable dataTable) {
	    
		try {
			CidadeForm cidadeForm = new CidadeForm();
			cidadeForm.setNome(dataTable.row(1).get(0));
			cidadeForm.setEstadoSigla(dataTable.row(1).get(1));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(asJsonString(cidadeForm), headers);
			ResponseEntity<?> response = restTemplate.exchange(DEFAULT_URL + url, 
					HttpMethod.POST, entity, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	
	}

	@Então("o cliente recebe {int} como confirmação de criação")
	public void o_cliente_recebe_como_confirmação_de_criação(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(int1, estadoCompartilhado.getResponse().getStatusCodeValue());
	}
	
	@E("o corpo da mensagem é vazio")
	public void o_corpo_da_mensagem_é_vazio() {
	    // Write code here that turns the phrase above into concrete actions
		assertNull(estadoCompartilhado.getResponse().getBody());
	}
	
	@E("possui o endereço do recurso no cabeçalho")
	public void possui_o_endereço_do_recurso_no_cabecalho() {
		assertNotNull(estadoCompartilhado.getResponse().getHeaders().getLocation());
	}
	
	/**
	 * Cenário 2
	 */

	@Dado("que não existe um estado {string} cadastrado")
	public void que_não_existe_um_estado_cadastrado(String sigla) {
		assertTrue(estadoRepository.findBySigla(sigla).isEmpty());
	}
	
	@Então("é retornado código {int} como resultado da operação")
	public void é_retornado_código_como_resultado_da_operação(Integer int1) {
	    assertEquals(int1, estadoCompartilhado.getResponse().getStatusCodeValue());
	}

	@Então("o corpo da mensagem contém informações de erro")
	public void o_corpo_da_mensagem_contém_informações_de_erro() {
	    assertNotNull(estadoCompartilhado.getResponse().getBody());
	}
	
}

package br.com.compasso.clientes.bdd.steps;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Sexo;
import io.cucumber.java.pt.Quando;


public class CadastraCliente extends SpringIntegrationTest {
	

	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	private String endpoint = "/clientes";

	@Quando("o usuário faz um POST para \\/clientes com as informações do cliente no corpo da mensagem")
	public void o_usuário_faz_um_POST_para_clientes_com_as_informações_do_cliente_no_corpo_da_mensagem() {
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(1L);
		clienteForm.setDataNascimento(LocalDate.of(1992, 6, 13));
		clienteForm.setSexo(Sexo.M);
		clienteForm.setNomeCompleto("Jovani Brasil");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(asJsonString(clienteForm), headers);
		ResponseEntity<?> response = restTemplate.exchange(DEFAULT_URL + endpoint, 
				HttpMethod.POST, entity, Void.class);
		estadoCompartilhado.setResponse(response);
	}
	
}

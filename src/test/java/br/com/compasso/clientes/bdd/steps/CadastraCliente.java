package br.com.compasso.clientes.bdd.steps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.Sexo;
import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.repositorios.EstadoRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;


public class CadastraCliente extends SpringIntegrationTest {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoCompartilhado estadoCompartilhado;
	
	@Dado("que existe uma cidade cadastrada no sistema com id {int}")
	public void que_existe_uma_cidade_cadastrada_no_sistema_com_id(Integer int1) {
	    if(cidadeRepository.findById((long)int1).isEmpty()) {
	    	Optional<Estado> optEstado = estadoRepository.findBySigla("RS");
			Estado estado = null;
	    	if(optEstado.isEmpty()) {
	    		estado = new Estado();
	    		estado.setSigla("RS");
	    		estado = estadoRepository.save(estado);
	    	}else {
	    		estado = optEstado.get();
	    	}
	    	
	    	cidadeRepository.save(new Cidade((long)int1, 
	    			"Porto Alegre", estado));
	    }
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
	    Optional<Cidade> optCidade = cidadeRepository.findById((long)id);
		if(optCidade.isPresent()) {
	    	cidadeRepository.deleteById((long)id);
	    }
	}

	
}

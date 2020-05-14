package br.com.compasso.clientes.bdd.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.compasso.clientes.repositorios.CidadeRepository;
import br.com.compasso.clientes.repositorios.ClienteRepository;
import br.com.compasso.clientes.repositorios.EstadoRepository;


public abstract class SpringIntegrationTest {

	@Autowired
	protected CidadeRepository cidadeRepository;
	@Autowired
	protected EstadoRepository estadoRepository;
	@Autowired
	protected ClienteRepository clienteRepository;
	
	@Autowired
	protected EstadoCompartilhado estadoCompartilhado;
			
	protected RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    protected final String DEFAULT_URL = "http://localhost:8080";
    
    public void limpaBancoDeDados() {
    	clienteRepository.deleteAll();
    	cidadeRepository.deleteAll();
    	estadoRepository.deleteAll();
    }
    
    protected ResponseEntity<Object> extracted(String url, HttpEntity<String> entity) {
		return restTemplate.exchange(DEFAULT_URL + url, 
				HttpMethod.POST, entity, Object.class);
	}
    
	protected void get(String url) {
		try {
			url = DEFAULT_URL + url;
			ResponseEntity<?> response = restTemplate
					.getForEntity(url, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	}
	
	protected void post(String endpoint, Object object) {
		try {
	    	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(asJsonString(object), headers);
			ResponseEntity<?> response = restTemplate.exchange(DEFAULT_URL + endpoint, 
					HttpMethod.POST, entity, Object.class);
			estadoCompartilhado.setResponse(response);			
		} catch (HttpStatusCodeException  e) {
			estadoCompartilhado.setResponse(ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                .body(e.getResponseBodyAsString()));
		}
	}
	
	protected void patch(String url, Object object) {
		try {
			url = DEFAULT_URL + url;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(asJsonString(object), headers);
			ResponseEntity<?> response = restTemplate.exchange(url, 
		    		HttpMethod.PATCH, entity, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpClientErrorException ex) {
			estadoCompartilhado.setResponse(ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
	                .body(ex.getResponseBodyAsString()));
		}
	}
	
	protected void delete(String endpoint) {
		try {
			String url = DEFAULT_URL +  endpoint;
			ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
			estadoCompartilhado.setResponse(response);
		} catch (HttpClientErrorException ex) {
			estadoCompartilhado.setResponse(ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
	                .body(ex.getResponseBodyAsString()));
		}
	}
    
    public String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
    
}

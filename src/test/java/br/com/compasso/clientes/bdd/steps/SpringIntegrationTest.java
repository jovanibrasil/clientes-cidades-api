package br.com.compasso.clientes.bdd.steps;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public abstract class SpringIntegrationTest {

	protected RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    protected final String DEFAULT_URL = "http://localhost:8080";
    
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

package br.com.compasso.clientes.bdd.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

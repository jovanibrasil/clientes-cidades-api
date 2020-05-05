package br.com.compasso.clientes.bdd;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.com.compasso.clientes.ClientesApiApplication;
import io.cucumber.java.Before;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ClientesApiApplication.class, loader = SpringBootContextLoader.class)
public class SpringContext {
	@Before
	public void setUp() {
		//Need this method so the cucumber will recognize this class as glue and load spring context configuration
	}
}

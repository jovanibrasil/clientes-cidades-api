package br.com.compasso.clientes.bdd.steps;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EstadoCompartilhado {
	
	ResponseEntity<?> response;

	public ResponseEntity<?> getResponse() {
		return response;
	}

	public void setResponse(ResponseEntity<?> response) {
		this.response = response;
	}

}

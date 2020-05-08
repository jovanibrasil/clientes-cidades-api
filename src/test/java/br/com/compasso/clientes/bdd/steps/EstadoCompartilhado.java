package br.com.compasso.clientes.bdd.steps;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EstadoCompartilhado {
	
	private ResponseEntity<?> response;
	private Object object;

	public ResponseEntity<?> getResponse() {
		return response;
	}

	public void setResponse(ResponseEntity<?> response) {
		this.response = response;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}

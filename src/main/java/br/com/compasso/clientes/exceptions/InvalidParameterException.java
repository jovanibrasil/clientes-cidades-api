package br.com.compasso.clientes.exceptions;

public class InvalidParameterException extends RuntimeException {

	private static final long serialVersionUID = -6264103178629182768L;

	public InvalidParameterException(String msg) {
		super(msg);
	}
	
}

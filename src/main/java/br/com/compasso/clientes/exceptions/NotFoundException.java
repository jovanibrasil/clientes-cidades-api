package br.com.compasso.clientes.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5764238859783538667L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
}

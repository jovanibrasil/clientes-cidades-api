package br.com.compasso.clientes.exceptions;

public class ErroValidacao {

	private final String mensagem;
	private final String campo;
	private final Object parametro;
	
	public ErroValidacao(String mensagem, String campo, Object parametro) {
		super();
		this.mensagem = mensagem;
		this.campo = campo;
		this.parametro = parametro;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public String getCampo() {
		return campo;
	}
	public Object getParametro() {
		return parametro;
	}
	
}

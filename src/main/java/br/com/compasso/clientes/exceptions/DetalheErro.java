package br.com.compasso.clientes.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalheErro<T> {

	private String mensagem;
	private int codigo;
	private LocalDateTime timestamp;
	private String status;
	private String nomeObjeto;
	private T errosAssociados;

	public DetalheErro(String message) {
		this.mensagem = message;
	}

	public String getMessage() {
		return mensagem;
	}

	public int getCode() {
		return codigo;
	}

	public String getStatus() {
		return status;
	}

	public String getObjectName() {
		return nomeObjeto;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public T getErrors() {
		return errosAssociados;
	}

	public static class Builder <T> {

		private String message;
		private int code;
		private LocalDateTime timestamp;
		private String status;
		private String objectName;
		private T errors;

		public Builder() {}

		public Builder<T> message(String message) {
			this.message = message;
			return this;
		}

		public Builder<T> code(int code) {
			this.code = code;
			return this;
		}

		public Builder<T> status(String status) {
			this.status = status;
			return this;
		}

		public Builder<T> objectName(String objectName) {
			this.objectName = objectName;
			return this;
		}

		public Builder<T> errors(T errors) {
			this.errors = errors;
			return this;
		}
		
		public Builder<T> timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public DetalheErro<T> build() {
			return new DetalheErro<T>(this);
		}

	}

	private DetalheErro(Builder<T> builder) {
		this.mensagem = builder.message;
		this.codigo = builder.code;
		this.status = builder.status;
		this.nomeObjeto = builder.objectName;
		this.errosAssociados = builder.errors;
		this.timestamp = builder.timestamp;
	}

}

package br.com.compasso.clientes.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class CidadeForm {
	
	@NotBlank(message = "Nome da cidade não pode ser branco ou nulo.")
	@Size(min = 3, max = 30, message = "Nome da cidade deve ter entre 3 e 30 caracteres.")
	@ApiModelProperty(required = true, example = "Porto Alegre")
	private String nome;
	@NotBlank(message = "Sigla do estado não pode ser branco ou nulo.")
	@Size(max = 2, min = 2, message = "Sigla de estado deve ter 2 caracteres.")
	@ApiModelProperty(required = true, example = "RS")
	private String estadoSigla;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEstadoSigla() {
		return estadoSigla;
	}
	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}
	
}

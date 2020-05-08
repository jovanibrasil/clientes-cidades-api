package br.com.compasso.clientes.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class AtualizacaoClienteForm {

	@NotBlank(message = "Nome do cliente não pode ser em branco ou nulo.")
	@Size(min = 2, max = 50, message = "Nome do cliente deve ter entre 2 e 50 caracteres.")
	@ApiModelProperty(required = true, example = "Fulano da Silva")
	private String nomeCompleto;

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
}

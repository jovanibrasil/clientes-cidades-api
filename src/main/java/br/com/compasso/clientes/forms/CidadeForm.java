package br.com.compasso.clientes.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CidadeForm {
	
	@NotBlank(message = "Nome da cidade não pode ser branco ou nulo.")
	@Size(min = 3, max = 30, message = "Nome da cidade deve ter entre 3 e 30 caracteres.")
	private String nome;
	@NotBlank(message = "Sigla do estado não pode ser branco ou nulo.")
	@Size(max = 2, min = 2, message = "Sigla de estado deve ter 2 caracteres.")
	private String estadoSigla;
	
}
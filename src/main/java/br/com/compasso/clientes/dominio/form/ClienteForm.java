package br.com.compasso.clientes.dominio.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.compasso.clientes.dominio.enumeration.Sexo;
import br.com.compasso.clientes.validator.EnumValidator;
import br.com.compasso.clientes.validator.DateValidAndBeforeValidator;
import io.swagger.annotations.ApiModelProperty;


public class ClienteForm {
	
	@NotBlank(message = "Nome do cliente não pode ser em branco ou nulo.")
	@Size(min = 2, max = 50, message = "Nome do cliente deve ter entre 2 e 50 caracteres.")
	@ApiModelProperty(required = true, example = "Fulano da Silva")
	private String nomeCompleto;
	
	@NotNull(message = "Data de nascimento não pode ser nula.")
	@DateValidAndBeforeValidator(message = "Data deve ter formato válido e estar no passado.")
	@ApiModelProperty(required = true, example = "13/06/1992")
	private String dataNascimento;
	
	@NotNull(message = "Sexo do cliente não pode ser nulo.")
	@EnumValidator(enumClass = Sexo.class, message = "Sexo deve ser um enum válido.")
	@ApiModelProperty(required = true, example = "M")
	private String sexo;
	
	@Digits(fraction = 0, integer = 5, message = "Identificador de cidade deve ser um número válido")
	@NotNull(message = "Identificador da cidade não pode ser nulo.")
	private String cidadeId;

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(String cidadeId) {
		this.cidadeId = cidadeId;
	}
	
}
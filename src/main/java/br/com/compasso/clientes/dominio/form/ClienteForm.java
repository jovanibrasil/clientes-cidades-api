package br.com.compasso.clientes.dominio.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.compasso.clientes.dominio.enumeration.Sexo;
import io.swagger.annotations.ApiModelProperty;


public class ClienteForm {
	
	@NotBlank(message = "Nome do cliente não pode ser em branco ou nulo.")
	@Size(min = 2, max = 50, message = "Nome do cliente deve ter entre 2 e 50 caracteres.")
	@ApiModelProperty(required = true, example = "Fulano da Silva")
	private String nomeCompleto;
	
	@NotNull(message = "Data de nascimento não pode ser nula.")
	@Past(message = "Data de nascimento deve ser anterior a data atual.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@ApiModelProperty(required = true, example = "13/06/1992")
	private LocalDate dataNascimento;
	
	@NotNull(message = "Sexo do cliente não pode ser nulo.")
	@ApiModelProperty(required = true, example = "M")
	private Sexo sexo;
	
	@NotNull(message = "Identificador da cidade não pode ser nulo.")
	private Long cidadeId;

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Long getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}
	
}
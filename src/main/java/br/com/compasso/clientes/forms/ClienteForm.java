package br.com.compasso.clientes.forms;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.compasso.clientes.modelos.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteForm {
	
	@NotBlank(message = "Nome do cliente não pode ser em branco ou nulo.")
	@Size(min = 2, max = 50, message = "Nome do cliente deve ter entre 2 e 50 caracteres.")
	private String nomeCompleto;
	
	@NotNull(message = "Data de nascimento não pode ser nula.")
	@Past(message = "Data de nascimento deve ser anterior a data atual.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@NotNull(message = "Sexo do cliente não pode ser nulo.")
	private Sexo sexo;
	
	@NotNull(message = "Identificador da cidade não pode ser nulo.")
	private Long cidadeId;
	
}

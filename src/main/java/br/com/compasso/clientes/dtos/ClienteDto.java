package br.com.compasso.clientes.dtos;

import java.time.LocalDate;

import br.com.compasso.clientes.modelos.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteDto {
	
	private Long id;
	private String nomeCompleto;
	private LocalDate dataNascimento;
	private int idade;
	private Sexo sexo;
	private Long idCidade;

}

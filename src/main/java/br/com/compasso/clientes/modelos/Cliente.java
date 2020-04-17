package br.com.compasso.clientes.modelos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter @Setter
@EqualsAndHashCode
public class Cliente {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id", nullable = false)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nomeCompleto;
	@Column(nullable = false)
	private LocalDateTime dataNascimento;
	@Column(nullable = false)
	private int idade;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Sexo sexo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cidade_id", referencedColumnName = "cidade_id", nullable = false)
	private Cidade cidade;
	
}

package br.com.compasso.clientes.modelos;

import java.time.LocalDate;
import java.time.Period;

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

import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id", nullable = false)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nomeCompleto;
	@Column(nullable = false)
	private LocalDate dataNascimento;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Sexo sexo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cidade_id", referencedColumnName = "cidade_id", nullable = false)
	private Cidade cidade;

	public Cliente(Long id, String nomeCompleto, LocalDate dataNascimento, Sexo sexo, Cidade cidade) {
		super();
		setId(id);
		setNomeCompleto(nomeCompleto);
		setDataNascimento(dataNascimento);
		setSexo(sexo);
		setCidade(cidade);
	}

	public Long getId() {
		return id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public int getIdade() {
		return Period.between(dataNascimento, LocalDate.now()).getYears();
	}

	public Sexo getSexo() {
		return sexo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public final void setId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Id não pode ser nulo.");
		}
		this.id = id;
	}
	
	public final void setNomeCompleto(String nomeCompleto) {
		if(nomeCompleto == null) {
			throw new IllegalArgumentException("Nome não pode ser nulo.");
		}
		this.nomeCompleto = nomeCompleto;
	}
	
	public final void setDataNascimento(LocalDate dataNascimento) {
		if(dataNascimento == null) {
			throw new IllegalArgumentException("Data de nascimento não pode ser nula.");
		}
		if(dataNascimento.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Data de nascimento deve ser no passado.");
		}
		this.dataNascimento = dataNascimento;
	}
	
	public final void setCidade(Cidade cidade) {
		if(cidade == null) {
			throw new IllegalArgumentException("Cidade não pode ser nula.");
		}
		this.cidade = cidade;
	}

	public void setSexo(Sexo sexo) {
		if(sexo == null) {
			throw new IllegalArgumentException("Sexo não pode ser nulo.");
		}
		this.sexo = sexo;
	}
	
}

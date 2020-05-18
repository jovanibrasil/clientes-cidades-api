package br.com.compasso.clientes.dominio;

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

import br.com.compasso.clientes.dominio.enumeration.Sexo;

@Entity
@Table(name = "clientes")
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
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cidade = cidade;
	}

	public Cliente() {}
	
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

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
}

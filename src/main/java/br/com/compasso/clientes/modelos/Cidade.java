package br.com.compasso.clientes.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "cidades")
@NoArgsConstructor
public final class Cidade {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "cidade_id", nullable = false, unique = true)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "estado_id", referencedColumnName = "estado_id", nullable = false)
	private Estado estado;

	public Cidade(Long id, String nome, Estado estado) {
		super();
		setId(id);
		setNome(nome);
		setEstado(estado);
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Estado getEstado() {
		return estado;
	}
	
	public void setId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Id não pode ser nula.");
		}
		this.id = id;
	}
	
	public void setNome(String nome) {
		if(nome == null) {
			throw new IllegalArgumentException("Nome não pode ser nulo.");
		}
		this.nome = nome;
	}

	public void setEstado(Estado estado) {
		if(estado == null) {
			throw new IllegalArgumentException("Estado não pode ser nulo");
		}
		this.estado = estado;
	}
	
}

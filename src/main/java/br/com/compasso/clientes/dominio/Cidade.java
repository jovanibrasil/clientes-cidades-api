package br.com.compasso.clientes.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidades")
public class Cidade {
	
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
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}
	
	public Cidade() {}
	
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
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}

package br.com.compasso.clientes.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estados")
public class Estado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estado_id", nullable = false)
	private Long id;
	@Column(nullable = false, length = 2)
	private String sigla;

	public Estado(Long id, String sigla) {
		this.id = id;
		this.sigla = sigla;
	}

	public Estado() {}
	
	public Long getId() {
		return id;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}

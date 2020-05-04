package br.com.compasso.clientes.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "estados")
@NoArgsConstructor
public final class Estado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estado_id", nullable = false)
	private Long id;
	@Column(nullable = false, length = 2)
	private String sigla;

	public Estado(Long id, String sigla) {
		setId(id);
		setSigla(sigla);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setId(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Id não pode ser nulo.");
		}
		this.id = id;
	}
	
	public void setSigla(String sigla) {
		if(sigla == null) {
			throw new IllegalArgumentException("Sigla não pode ser nula.");
		}
		this.sigla = sigla;
	}
	
}

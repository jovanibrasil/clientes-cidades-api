package br.com.compasso.clientes.modelos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estados")
@Getter @Setter
public class Estado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estado_id", nullable = false)
	private Long id;
	@Column(nullable = false, length = 2)
	private String sigla;
	
}

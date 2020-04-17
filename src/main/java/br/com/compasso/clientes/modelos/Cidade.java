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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cidades")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Cidade {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "cidade_id", nullable = false, unique = true)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false, length = 30)
	@EqualsAndHashCode.Include
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "estado_id", referencedColumnName = "estado_id", nullable = false)
	private Estado estado;
	
}

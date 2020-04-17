package br.com.compasso.clientes.modelos;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cidades")
@Getter @Setter
@NoArgsConstructor
public class Cidade {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cidade_id", nullable = false, unique = true)
	private Long id;
	
	@EmbeddedId
	private CidadeId cidadeIdComposta; // chave primária composta
	
	public Cidade(String nome, Long estadoId) {
		Estado estado = new Estado();
		estado.setId(estadoId);
		this.cidadeIdComposta = new CidadeId(nome, estado);
	}
	
}

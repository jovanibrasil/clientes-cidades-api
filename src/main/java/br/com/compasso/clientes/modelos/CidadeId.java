package br.com.compasso.clientes.modelos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Chave primária composta. Está sendo usada para garantir
 * que não haverá cidades com o mesmo nome em um mesmo estado.
 * 
 * @author jovani.brasil
 *
 */
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CidadeId implements Serializable {
	
	private static final long serialVersionUID = -3849528464904636854L;

	@Column(nullable = false, length = 30)
	@EqualsAndHashCode.Include
	private String nome;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "estado_id", referencedColumnName = "estado_id", nullable = false)
    @EqualsAndHashCode.Include
	private Estado estado;
	
}

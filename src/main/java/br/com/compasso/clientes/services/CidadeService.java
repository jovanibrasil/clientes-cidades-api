package br.com.compasso.clientes.services;


import java.util.List;

import br.com.compasso.clientes.modelos.Cidade;

public interface CidadeService {
	
	Cidade salvaCidade(Cidade cidade);
	List<Cidade> buscaPorNome(String nomeCidade);
	Cidade buscaPorEstado(String nomeCidade, String siglaEstado);
	Cidade buscaPorId(Long cidadeId);

}

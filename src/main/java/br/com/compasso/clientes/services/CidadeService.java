package br.com.compasso.clientes.services;


import br.com.compasso.clientes.modelos.Cidade;

public interface CidadeService {
	
	Cidade salvaCidade(Cidade cidade);
	Cidade buscaPorNome(String nomeCidade);
	Cidade buscaPorEstado(String nomeCidade, String siglaEstado);

}

package br.com.compasso.clientes.services;


import java.util.List;

import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.dtos.CidadeDTO;
import br.com.compasso.clientes.modelos.forms.CidadeForm;

public interface CidadeService {
	
	CidadeDTO salvaCidade(CidadeForm cidadeForm);
	List<CidadeDTO> buscaPorNome(String nomeCidade);
	Cidade buscaPorId(Long cidadeId);
	List<CidadeDTO> buscaPorEstado(String estadoSigla);

}

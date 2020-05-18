package br.com.compasso.clientes.service;


import java.util.List;

import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;

public interface CidadeService {
	
	CidadeDTO salvaCidade(CidadeForm cidadeForm);
	List<CidadeDTO> buscaPorNome(String nomeCidade);
	Cidade buscaPorId(Long cidadeId);
	List<CidadeDTO> buscaPorEstado(String estadoSigla);

}

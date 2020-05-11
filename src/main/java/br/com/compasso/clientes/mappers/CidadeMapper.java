package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.dtos.CidadeDTO;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;

public interface CidadeMapper {
	CidadeDTO cidadeToCidadeDto(Cidade cidade);
	Cidade cidadeFormToCidade(CidadeForm cidadeForm);
}

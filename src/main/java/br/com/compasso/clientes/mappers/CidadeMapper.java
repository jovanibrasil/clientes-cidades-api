package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.dtos.CidadeDTO;
import br.com.compasso.clientes.modelos.forms.CidadeForm;

public interface CidadeMapper {
	CidadeDTO cidadeToCidadeDto(Cidade cidade);
	Cidade cidadeFormToCidade(CidadeForm cidadeForm);
}

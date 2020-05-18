package br.com.compasso.clientes.mapper;

import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;

public interface CidadeMapper {
	CidadeDTO cidadeToCidadeDto(Cidade cidade);
	Cidade cidadeFormToCidade(CidadeForm cidadeForm);
}

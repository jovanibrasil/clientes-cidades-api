package br.com.compasso.clientes.mappers;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;

public interface CidadeMapper {
	CidadeDto cidadeToCidadeDto(Cidade cidade);
	Cidade cidadeFormToCidade(CidadeForm cidadeForm);
}

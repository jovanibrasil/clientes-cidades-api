package br.com.compasso.clientes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;

@Mapper
public interface CidadeMapper {
	@Mapping(source = "estado.sigla", target = "estadoSigla")
	CidadeDto cidadeToCidadeDto(Cidade cidade);
	Cidade cidadeFormToCidade(CidadeForm cidadeForm);
	
}

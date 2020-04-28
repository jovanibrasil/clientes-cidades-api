package br.com.compasso.clientes.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.repositorios.EstadoRepository;

public abstract class CidadeMapperDecorator implements CidadeMapper {

	private CidadeMapper cidadeMapper;
	private EstadoRepository estadoRepository;
	
	@Override
	public Cidade cidadeFormToCidade(CidadeForm cidadeForm) {
		Cidade cidade = cidadeMapper.cidadeFormToCidade(cidadeForm);
		Optional<Estado> estado = estadoRepository.findBySigla(cidadeForm.getEstadoSigla());
		
		if(estado.isEmpty()) {
			throw new NotFoundException("Estado não encontrado.");
		}
		
		cidade.setEstado(estado.get());
		return cidade;
	}
	
	@Autowired
	public void setEstadoRepository(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	@Autowired
	public void setCidadeMapper(CidadeMapper cidadeMapper) {
		this.cidadeMapper = cidadeMapper;
	}
	
}

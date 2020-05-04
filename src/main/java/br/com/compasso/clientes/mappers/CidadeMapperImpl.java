package br.com.compasso.clientes.mappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.repositorios.EstadoRepository;

@Component
public class CidadeMapperImpl implements CidadeMapper {

	private final EstadoRepository estadoRepository;
	
	public CidadeMapperImpl(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	@Override
	public CidadeDto cidadeToCidadeDto(Cidade cidade) {
		if (cidade == null) {
			return null;
		}

		CidadeDto cidadeDto = new CidadeDto();

		cidadeDto.setEstadoSigla(cidade.getEstado().getSigla());
		cidadeDto.setId(cidade.getId());
		cidadeDto.setNome(cidade.getNome());

		return cidadeDto;
	}

	@Override
	public Cidade cidadeFormToCidade(CidadeForm cidadeForm) {
		if (cidadeForm == null) {
			return null;
		}

		Cidade cidade = new Cidade();

		cidade.setNome(cidadeForm.getNome());
		
		Optional<Estado> estado = estadoRepository.findBySigla(cidadeForm.getEstadoSigla());
		
		if(estado.isEmpty()) {
			throw new NotFoundException("Estado não encontrado.");
		}
		
		cidade.setEstado(estado.get());
		
		return cidade;
	}

}

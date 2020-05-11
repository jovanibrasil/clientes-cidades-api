package br.com.compasso.clientes.mappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.dtos.CidadeDTO;
import br.com.compasso.clientes.modelos.forms.CidadeForm;
import br.com.compasso.clientes.repositorios.EstadoRepository;

@Component
public class CidadeMapperImpl implements CidadeMapper {

	private final EstadoRepository estadoRepository;
	
	public CidadeMapperImpl(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	@Override
	public CidadeDTO cidadeToCidadeDto(Cidade cidade) {
		if (cidade == null) {
			return null;
		}

		CidadeDTO cidadeDto = new CidadeDTO();

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
			throw new InvalidParameterException("Estado " + cidadeForm.getEstadoSigla() + " não encontrado.");
		}
		
		cidade.setEstado(estado.get());
		
		return cidade;
	}

}

package br.com.compasso.clientes.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.repositorios.EstadoRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CidadeMapperTest {

	@InjectMocks
	private CidadeMapperImpl cidadeMapper;
	@Mock
	private EstadoRepository estadoRepository;
	
	private static final long ID_CIDADE = 1L;
	private static final long ID_ESTADO = 2L;
	private static final String NOME_CIDADE = "Porto Alegre";
	private static final String SIGLA_ESTADO = "RS";

	@BeforeEach
	void setUp() throws Exception {		
		MockitoAnnotations.initMocks(this);
		cidadeMapper = new CidadeMapperImpl(estadoRepository);
	}
	
	@Test
	void testConverteCidadeToCidadeDto() {
		Cidade cidade = new Cidade();
		cidade.setId(ID_CIDADE);
		cidade.setNome(NOME_CIDADE);
		cidade.setEstado(new Estado(ID_ESTADO, SIGLA_ESTADO));
		
		CidadeDto cidadeDto = cidadeMapper.cidadeToCidadeDto(cidade);
		
		assertEquals(ID_CIDADE, cidadeDto.getId());
		assertEquals(NOME_CIDADE, cidadeDto.getNome());
		assertEquals(SIGLA_ESTADO, cidadeDto.getEstadoSigla());
	}

	@Test
	void testCidadeFormToCidade() {
		Estado estado = new Estado(ID_CIDADE, SIGLA_ESTADO);
		when(estadoRepository.findBySigla(SIGLA_ESTADO)).thenReturn(Optional.of(estado));
		
		CidadeForm cidadeForm = new CidadeForm();
		cidadeForm.setEstadoSigla(SIGLA_ESTADO);
		cidadeForm.setNome(NOME_CIDADE);
		
		Cidade cidade = cidadeMapper.cidadeFormToCidade(cidadeForm);
		
		assertEquals(NOME_CIDADE, cidade.getNome());
		assertEquals(estado, cidade.getEstado());
	}
	
	@Test
	void testCidadeFormNullToCidade() {
		assertEquals(null, cidadeMapper.cidadeFormToCidade(null));
	}
	
	@Test
	void testCidadeToCidadeDto() {
		assertEquals(null, cidadeMapper.cidadeToCidadeDto(null));
	}
	
	@Test
	void testCidadeFormIdCidadeInvalida() {
		when(estadoRepository.findBySigla(SIGLA_ESTADO)).thenReturn(Optional.empty());
		
		CidadeForm cidadeForm = new CidadeForm();
		cidadeForm.setEstadoSigla(SIGLA_ESTADO);
		cidadeForm.setNome(NOME_CIDADE);
		
		assertThrows(InvalidParameterException.class, () -> {
			cidadeMapper.cidadeFormToCidade(cidadeForm);
		});
	}
	
}

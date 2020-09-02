package br.com.compasso.clientes.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.Estado;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.enumeration.Sexo;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.mapper.ClienteMapperImpl;
import br.com.compasso.clientes.service.CidadeService;
import br.com.compasso.clientes.util.DateUtils;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ClienteMapperTest {

	@InjectMocks
	private ClienteMapperImpl clienteMapper;
	@MockBean
	private CidadeService cidadeService;
	
	private static final String NOME_CLIENTE = "Jovani Brasil";
	private static final String NOME_CIDADE = "Porto Alegre";
	private static final String ESTADO_SIGLA = "RS";
	private static final Sexo SEXO_CLIENTE = Sexo.M;
	private static final long CLIENTE_ID = 3L;
	private static final long CIDADE_ID = 2L;
	private static final long ESTADO_ID = 1L;
	private Cliente cliente;
	private Cidade cidade;
	private Estado estado;
	private final LocalDate DATA_NASCIMENTO = LocalDate.of(1992, 6, 13);
	
	@BeforeEach
	void setUp() throws Exception {		
		MockitoAnnotations.initMocks(this);
		clienteMapper = new ClienteMapperImpl(cidadeService);
		estado = new Estado(ESTADO_ID, ESTADO_SIGLA);
		cidade = new Cidade(CIDADE_ID, NOME_CIDADE, estado);
		cliente = new Cliente(CLIENTE_ID, NOME_CLIENTE, DATA_NASCIMENTO, SEXO_CLIENTE, cidade);
	}

	@Test
	void clienteFormToCliente_QuandoClienteFormValido_EsperaClienteValido() {
		when(cidadeService.buscaPorId(CIDADE_ID)).thenReturn(cidade);
		
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cliente.getCidade().getId().toString());
		clienteForm.setDataNascimento(DateUtils.converteLocalDateToString(cliente.getDataNascimento()));
		clienteForm.setNomeCompleto(cliente.getNomeCompleto().toString());
		clienteForm.setSexo(cliente.getSexo().toString());
		
		Cliente clienteMapeado = clienteMapper.clienteFormToCliente(clienteForm);
		assertEquals(cliente.getCidade(), clienteMapeado.getCidade());
		assertEquals(cliente.getDataNascimento(), clienteMapeado.getDataNascimento());
		assertEquals(cliente.getNomeCompleto(), clienteMapeado.getNomeCompleto());
		assertEquals(cliente.getSexo(), clienteMapeado.getSexo());
		assertEquals(cliente.getCidade(), clienteMapeado.getCidade());
	}
	
	@Test
	void clienteFormToCliente_QuandoIdCidadeInvalido_EsperaNotFoundException() {
		Long cidadeId = 0L;
		when(cidadeService.buscaPorId(cidadeId )).thenThrow(NotFoundException.class);
		
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cidadeId.toString());
		clienteForm.setDataNascimento(DateUtils.converteLocalDateToString(cliente.getDataNascimento()));
		clienteForm.setNomeCompleto(cliente.getNomeCompleto().toString());
		clienteForm.setSexo(cliente.getSexo().toString());
		
		assertThrows(InvalidParameterException.class, () -> {
			clienteMapper.clienteFormToCliente(clienteForm);
		});
	}

	@Test
	void atualizacaoClienteFormToCliente_QuandoFormValido_EsperaClienteValido() {
		AtualizacaoClienteForm atualizacaoClienteForm = new AtualizacaoClienteForm();
		String novoNome = "João Silva";
		atualizacaoClienteForm.setNomeCompleto(novoNome);
		
		Cliente clienteMapeado = clienteMapper.atualizacaoClienteFormToCliente(atualizacaoClienteForm);
		assertEquals(novoNome, clienteMapeado.getNomeCompleto());
	}

	@Test
	void clienteToClienteDto_QuandoClienteValido_EsperaClienteDtoValido() {
		ClienteDTO clienteDto = clienteMapper.clienteToClienteDto(cliente);
		assertEquals(cliente.getId(), clienteDto.getId());
		assertEquals(cliente.getIdade(), clienteDto.getIdade());
		assertEquals(cliente.getNomeCompleto(), clienteDto.getNomeCompleto());
		assertEquals(cliente.getDataNascimento(), clienteDto.getDataNascimento());
		assertEquals(cliente.getCidade().getId(), clienteDto.getIdCidade());
		assertEquals(cliente.getSexo(), clienteDto.getSexo());
	}
	
	@Test
	void clienteToClienteDto_QuandoClienteNull_EsperaNull() {
		assertEquals(null, clienteMapper.clienteToClienteDto(null));
	}
	
	@Test
	void atualizacaoClienteFormToCliente_QuandoAtualizacaoClienteNull_EsperaNull() {
		assertEquals(null, clienteMapper.atualizacaoClienteFormToCliente(null));
	}

	@Test
	void clienteFormToCliente_QuandoClienteFormNull_EsperaNull() {
		assertEquals(null, clienteMapper.clienteFormToCliente(null));
	}
}

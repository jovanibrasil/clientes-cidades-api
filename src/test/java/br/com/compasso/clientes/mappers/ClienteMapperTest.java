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

import br.com.compasso.clientes.dtos.ClienteDTO;
import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.Sexo;
import br.com.compasso.clientes.services.CidadeService;

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
	void testClienteFormToCliente() {
		when(cidadeService.buscaPorId(CIDADE_ID)).thenReturn(cidade);
		
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cliente.getCidade().getId());
		clienteForm.setDataNascimento(cliente.getDataNascimento());
		clienteForm.setNomeCompleto(cliente.getNomeCompleto());
		clienteForm.setSexo(cliente.getSexo());
		
		Cliente clienteMapeado = clienteMapper.clienteFormToCliente(clienteForm);
		assertEquals(cliente.getCidade(), clienteMapeado.getCidade());
		assertEquals(cliente.getDataNascimento(), clienteMapeado.getDataNascimento());
		assertEquals(cliente.getNomeCompleto(), clienteMapeado.getNomeCompleto());
		assertEquals(cliente.getSexo(), clienteMapeado.getSexo());
		assertEquals(cliente.getCidade(), clienteMapeado.getCidade());
	}
	
	@Test
	void testClienteFormToClienteIdCidadeInvalido() {
		Long cidadeId = 0L;
		when(cidadeService.buscaPorId(cidadeId )).thenThrow(NotFoundException.class);
		
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cidadeId);
		clienteForm.setDataNascimento(cliente.getDataNascimento());
		clienteForm.setNomeCompleto(cliente.getNomeCompleto());
		clienteForm.setSexo(cliente.getSexo());
		
		assertThrows(InvalidParameterException.class, () -> {
			clienteMapper.clienteFormToCliente(clienteForm);
		});
	}

	@Test
	void testAtualizacaoClienteFormToCliente() {
		AtualizacaoClienteForm atualizacaoClienteForm = new AtualizacaoClienteForm();
		String novoNome = "João Silva";
		atualizacaoClienteForm.setNomeCompleto(novoNome);
		
		Cliente clienteMapeado = clienteMapper.atualizacaoClienteFormToCliente(atualizacaoClienteForm);
		assertEquals(novoNome, clienteMapeado.getNomeCompleto());
	}

	@Test
	void testClienteToClienteDto() {
		ClienteDTO clienteDto = clienteMapper.clienteToClienteDto(cliente);
		assertEquals(cliente.getId(), clienteDto.getId());
		assertEquals(cliente.getIdade(), clienteDto.getIdade());
		assertEquals(cliente.getNomeCompleto(), clienteDto.getNomeCompleto());
		assertEquals(cliente.getDataNascimento(), clienteDto.getDataNascimento());
		assertEquals(cliente.getCidade().getId(), clienteDto.getIdCidade());
		assertEquals(cliente.getSexo(), clienteDto.getSexo());
	}
	
	@Test
	void testClienteNulltoClienteDto() {
		assertEquals(null, clienteMapper.clienteToClienteDto(null));
	}
	
	@Test
	void testAtualizacaoClienteNullFormToCliente() {
		assertEquals(null, clienteMapper.atualizacaoClienteFormToCliente(null));
	}

	@Test
	void testClienteFormNullToCliente() {
		assertEquals(null, clienteMapper.clienteFormToCliente(null));
	}
}

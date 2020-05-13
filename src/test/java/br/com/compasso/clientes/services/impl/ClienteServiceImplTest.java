package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.mappers.ClienteMapper;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.dtos.ClienteDTO;
import br.com.compasso.clientes.modelos.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.modelos.forms.ClienteForm;
import br.com.compasso.clientes.repositorios.ClienteRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ClienteServiceImplTest {

	@InjectMocks
	private ClienteServiceImpl clientService;
	@MockBean
	private ClienteMapper clienteMapper;
	@MockBean
	private ClienteRepository clientRepository;

	private ClienteForm clienteForm;
	private ClienteDTO clienteDto;
	private AtualizacaoClienteForm atualizaClienteForm;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clientService = new ClienteServiceImpl(clientRepository, clienteMapper);
		
		cliente = ScenarioFactory.criaClienteJoao();
		clienteForm = ScenarioFactory.criaClienteFormJoao();
		clienteDto = ScenarioFactory.criaClienteDTOJoao();
		atualizaClienteForm = ScenarioFactory.criaClienteFormAlteracaoJoao();
	}

	/**
	 * Testa salvamento com sucesso de um cliente.
	 * 
	 */
	@Test
	void testSalvaCliente() {
		when(clienteMapper.clienteFormToCliente(any())).thenReturn(cliente);
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clientRepository.save(cliente)).then(new Answer<Cliente>() {
			@Override
			public Cliente answer(InvocationOnMock invocation) throws Throwable {
				Cliente cliente = (Cliente) invocation.getArgument(0);
				cliente.setId(1L);
				return cliente;
			}
		});
		assertEquals(1L, clientService.salvaCliente(clienteForm).getId());
	}
	
	/**
	 * Testa busca de cliente por id.
	 * 
	 */
	@Test
	void testBuscaClientePorId() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		assertNotNull(clientService.buscaPorId(cliente.getId()));
	}
	
	/**
	 * Testa busca de cliente não existente por id.
	 * 
	 */
	@Test
	void testBuscaClienteNaoExistentePorId() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.empty());
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		assertThrows(NotFoundException.class, () -> {
			clientService.buscaPorId(cliente.getId());
		}); 
	}

	/**
	 * Testa busca de cliente por nome.
	 * 
	 */
	@Test
	void testBuscaClientePorNome() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clientRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto()))
			.thenReturn(Arrays.asList(cliente));
		assertEquals(1, clientService.buscaPorNome(cliente.getNomeCompleto()).size());
	}
	
	/**
	 * Testa busca de cliente não existente por nome.
	 * 
	 */
	@Test
	void testBuscaClienteNaoExistentePorNome() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clientRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto()))
			.thenReturn(Arrays.asList());
		assertEquals(0, clientService.buscaPorNome(cliente.getNomeCompleto()).size());
	}
	
	/**
	 * Testa remoção de cliente por id.
	 * 
	 */
	@Test
	void testDeleteClientePorId() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		doNothing().when(clientRepository).delete(cliente);
		clientService.removeCliente(cliente.getId());
	}
	
	/**
	 * Testa remoção de cliente não existente por id.
	 * 
	 */
	@Test
	void testDeleteClienteNaoExistentePorId() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			clientService.removeCliente(cliente.getId());
		}); 
	}
	
	/**
	 * Testa alteração de cliente por id.
	 * 
	 */
	@Test
	void testAlteraClientePorId() {
		when(clienteMapper.clienteToClienteDto(any())).thenReturn(clienteDto);
		when(clienteMapper.atualizacaoClienteFormToCliente(any())).thenReturn(cliente);
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		when(clientRepository.save(cliente)).then(new Answer<Cliente>() {
			@Override
			public Cliente answer(InvocationOnMock invocation) throws Throwable {
				return (Cliente) invocation.getArgument(0);
			}
		});
		atualizaClienteForm.setId(cliente.getId());
		ClienteDTO cliente = clientService.alteraCliente(atualizaClienteForm);
		assertEquals(atualizaClienteForm.getNomeCompleto(), cliente.getNomeCompleto());
	}
	
	/**
	 * Testa alteração de cliente não existente por id.
	 * 
	 */
	@Test
	void testAlteraClienteNaoExistentePorId() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clienteMapper.atualizacaoClienteFormToCliente(any())).thenReturn(cliente);
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			clientService.alteraCliente(atualizaClienteForm);
		}); 
	}
	
}

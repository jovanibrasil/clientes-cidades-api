package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.mapper.ClienteMapper;
import br.com.compasso.clientes.repositorio.ClienteRepository;
import br.com.compasso.clientes.service.impl.ClienteServiceImpl;

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
	void salvaCliente_QuandoClienteCorreto_EsperaPorClienteSalvoComIdValido() {
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
	void buscaPorId_QuandoExisteClienteComId_EsperaClienteNoRetorno() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		assertNotNull(clientService.buscaPorId(cliente.getId()));
	}
	
	/**
	 * Testa busca de cliente não existente por id.
	 * 
	 */
	@Test
	void buscaPorId_QuandoNaoExisteClienteComId_EsperaNotFoundException() {
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
	void buscaPorNome_QuandoExisteClienteComNome_EsperaClienteNoRetorno() {
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
	void buscaPorNome_QuandoNaoExisteClienteComNome_EsperaListaVazia() {
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
	void removeCliente_QuandoExisteClienteComId_EsperaNenhumErroOuException() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		doNothing().when(clientRepository).delete(cliente);
		assertDoesNotThrow(() -> {
			clientService.removeCliente(cliente.getId());
		});
	}
	
	/**
	 * Testa remoção de cliente não existente por id.
	 * 
	 */
	@Test
	void removeCliente_QuandoNaoExisteClienteComId_EsperaNotFoundException() {
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
	void alteraCliente_QuandoExisteCliente_EsperaClienteModificadoNoRetorno() {
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
	void alteraCliente_QuandoNaoExisteCliente_EsperaNotFoundException() {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clienteMapper.atualizacaoClienteFormToCliente(any())).thenReturn(cliente);
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			clientService.alteraCliente(atualizaClienteForm);
		}); 
	}
	
}

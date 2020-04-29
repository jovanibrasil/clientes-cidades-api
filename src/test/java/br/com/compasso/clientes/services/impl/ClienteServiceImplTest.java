package br.com.compasso.clientes.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.Sexo;
import br.com.compasso.clientes.repositorios.ClienteRepository;

@ExtendWith(SpringExtension.class)
class ClienteServiceImplTest {

	@InjectMocks
	private ClienteServiceImpl clientService;
	@MockBean
	private ClienteRepository clientRepository;

	private Cidade cidade;
	private Estado estado;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clientService = new ClienteServiceImpl(clientRepository);

		estado = new Estado();
		estado.setSigla("RS");
		cidade = new Cidade();
		cidade.setNome("Porto Alegre");
		cidade.setEstado(estado);
		cliente = new Cliente();
		cliente.setCidade(cidade);
		cliente.setIdade(30);
		cliente.setNomeCompleto("João Silva");
		cliente.setSexo(Sexo.M);
		cliente.setDataNascimento(LocalDate.now().minusYears(30L));
	}

	/**
	 * Testa salvamento com sucesso de um cliente.
	 * 
	 */
	@Test
	void testSalvaCliente() {
		when(clientRepository.save(cliente)).then(new Answer<Cliente>() {
			@Override
			public Cliente answer(InvocationOnMock invocation) throws Throwable {
				Cliente cliente = (Cliente) invocation.getArgument(0);
				cliente.setId(1L);
				return cliente;
			}
		});
		assertEquals(1L, clientService.salvaCliente(cliente).getId());
	}
	
	/**
	 * Testa busca de cliente por id.
	 * 
	 */
	@Test
	void testBuscaClientePorId() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		assertNotNull(clientService.buscaPorId(cliente.getId()));
	}
	
	/**
	 * Testa busca de cliente não existente por id.
	 * 
	 */
	@Test
	void testBuscaClienteNaoExistentePorId() {
		when(clientRepository.findById(cliente.getId())).thenThrow(NotFoundException.class);
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
		when(clientRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto()))
			.thenReturn(Arrays.asList(cliente));
		assertEquals(1, clientRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto()).size());
	}
	
	/**
	 * Testa busca de cliente não existente por nome.
	 * 
	 */
	@Test
	void testBuscaClienteNaoExistentePorNome() {
		when(clientRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto()))
			.thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> {
			clientService.buscaPorNome(cliente.getNomeCompleto());
		}); 
	}
	
	/**
	 * Testa remoção de cliente por id.
	 * 
	 */
	@Test
	void testDeleteClientePorId() {
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		doNothing().when(clientRepository).delete(cliente);
	}
	
	/**
	 * Testa remoção de cliente não existente por id.
	 * 
	 */
	@Test
	void testDeleteClienteNaoExistentePorId() {
		when(clientRepository.findById(cliente.getId())).thenThrow(NotFoundException.class);
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
		when(clientRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
		Cliente clienteAlteracao = new Cliente();
		clienteAlteracao.setId(cliente.getId());
		String nome = "Jovanio";
		clienteAlteracao.setNomeCompleto(nome);
		Cliente cliente = clientService.alteraCliente(clienteAlteracao);
		assertEquals(nome, cliente.getNomeCompleto());
	}
	
	/**
	 * Testa alteração de cliente não existente por id.
	 * 
	 */
	@Test
	void testAlteraClienteNaoExistentePorId() {
		when(clientRepository.findById(cliente.getId())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> {
			Cliente clienteAlteracao = new Cliente();
			clienteAlteracao.setId(cliente.getId());
			clienteAlteracao.setNomeCompleto("Jovanir");
			clientService.alteraCliente(clienteAlteracao);
		}); 
	}
	
}

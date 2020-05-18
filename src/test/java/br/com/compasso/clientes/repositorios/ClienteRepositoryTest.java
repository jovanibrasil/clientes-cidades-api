package br.com.compasso.clientes.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cidade;
import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.Estado;
import br.com.compasso.clientes.repositorio.CidadeRepository;
import br.com.compasso.clientes.repositorio.ClienteRepository;
import br.com.compasso.clientes.repositorio.EstadoRepository;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClienteRepositoryTest {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Cidade cidade;
	private Estado estado;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		estado = estadoRepository.save(ScenarioFactory.criaEstadoRSIdNull());
		cidade = ScenarioFactory.criaCidadePoaIdNull();
		cidade.setEstado(estado);
		cidade = cidadeRepository.save(cidade);
		cliente = ScenarioFactory.criaClienteJoao();
		cliente.setCidade(cidade);
	}
	
	/**
	 * Testa salvamento de um cliente válido.
	 * 
	 */
	@Test
	public void save_QuandoClienteValido_EsperaRetornoClienteSalvo() {
		cliente = clienteRepository.save(cliente);
		assertNotNull(cliente.getId());
		assertNotNull(cliente.getCidade());
		assertNotNull(cliente.getDataNascimento());
	}
		
	/**
	 * Testa a busca por ID de um cliente cadastrado no sistema.
	 */
	@Test
	public void findById_QuandoClienteExiste_EsperaOptionalNaoVazio() {
		cliente = clienteRepository.save(cliente);
		Optional<Cliente> optClient = clienteRepository.findById(cliente.getId());
		assertTrue(optClient.isPresent());
	}
	
	/**
	 * Testa a busca por Nome de um cliente cadastrado no sistema.
	 */
	@Test
	public void findByNomeCompletoContainingIgnoreCase_QuandoExisteUmCliente_EsperaListaComUmCliente() {
		cliente = clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto());
		assertEquals(1, clientes.size());
	}
	
	/**
	 * Testa a busca por Nome de um cliente cadastrado no sistema. Desta vez existem mais
	 * de um registro para o nome informado.
	 */
	@Test
	public void findByNomeCompletoContainingIgnoreCase_QuandoExisteDoisCliente_EsperaListaComDoisClientes() {
		cliente = clienteRepository.save(cliente);
		Cliente clienteMaria = ScenarioFactory.criaClienteMaria();
		clienteMaria.setCidade(cidade);
		clienteRepository.save(clienteMaria);
		List<Cliente> clientes = clienteRepository.findByNomeCompletoContainingIgnoreCase("Silva");
		assertEquals(2, clientes.size());
	}
	
}

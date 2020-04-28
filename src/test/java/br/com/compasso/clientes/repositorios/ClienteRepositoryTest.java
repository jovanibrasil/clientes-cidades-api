package br.com.compasso.clientes.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.Sexo;

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
		estado = new Estado();
		estado.setSigla("RS");
		estado = estadoRepository.save(estado);
		cidade = new Cidade();
		cidade.setNome("Porto Alegre");
		cidade.setEstado(estado);
		cidade = cidadeRepository.save(cidade);
		cliente = new Cliente();
		cliente.setCidade(cidade);
		cliente.setIdade(30);
		cliente.setNomeCompleto("João Silva");
		cliente.setSexo(Sexo.M);
		cliente.setDataNascimento(LocalDate.now().minusYears(30L));
	}
	
	/**
	 * Testa salvamento de um cliente válido.
	 * 
	 */
	@Test
	public void testSalvaClienteValido() {
		cliente = clienteRepository.save(cliente);
		assertNotNull(cliente.getId());
		assertNotNull(cliente.getCidade());
		assertNotNull(cliente.getDataNascimento());
	}
	
	/**
	 * Testa salvamento de um cliente nula.
	 * 
	 */
	@Test
	public void testSalvaClienteComCidadeNull() {
		cliente.setCidade(null);
		assertThrows(DataIntegrityViolationException.class, () -> {
			clienteRepository.save(cliente);
		});
	}
	
	/**
	 * Testa a busca por ID de um cliente cadastrado no sistema.
	 */
	@Test
	public void testBuscaClientePorId() {
		cliente = clienteRepository.save(cliente);
		Optional<Cliente> optClient = clienteRepository.findById(cliente.getId());
		assertTrue(optClient.isPresent());
		assertEquals(cliente.getId(), optClient.get().getId());
	}
	
	/**
	 * Testa a busca por Nome de um cliente cadastrado no sistema.
	 */
	@Test
	public void testBuscaClientePorNome() {
		cliente = clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findByNomeCompletoContainingIgnoreCase(cliente.getNomeCompleto());
		assertEquals(1, clientes.size());
	}
	
	/**
	 * Testa a busca por Nome de um cliente cadastrado no sistema. Desta vez existem mais
	 * de um registro para o nome informado.
	 */
	@Test
	public void testBuscaClientePorNomeComMaisDeUmResultado() {
		cliente.setNomeCompleto("Jovani");
		cliente = clienteRepository.save(cliente);
		entityManager.detach(cliente);
		cliente.setId(null);
		cliente.setNomeCompleto("Jovanio");
		clienteRepository.save(cliente);
		List<Cliente> clientes = clienteRepository.findByNomeCompletoContainingIgnoreCase("Jovani");
		assertEquals(2, clientes.size());
	}
	
}

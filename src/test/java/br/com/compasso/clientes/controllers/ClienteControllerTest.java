package br.com.compasso.clientes.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.compasso.clientes.dtos.ClienteDto;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.forms.ClienteForm;
import br.com.compasso.clientes.mappers.ClienteMapper;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.Sexo;
import br.com.compasso.clientes.services.ClienteService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteControllerTest {

	@MockBean
	private ClienteService clienteService;
	@MockBean
	private ClienteMapper clienteMapper;
	@Autowired
	private MockMvc mvc;
	
	private ClienteForm clienteForm;
	private ClienteDto clienteDto;
	private Cliente cliente;
	private Cidade cidade;
	private Estado estado;
	
	@BeforeEach
	void setUp() throws Exception {
		estado = new Estado(1L, "RS");;
		cidade = new Cidade(1L, "Porto Alegre", estado);
		
		LocalDate aniversario = LocalDate.now().minusYears(30);
		cliente = new Cliente(2L, "João", aniversario, Sexo.M, cidade);
		
		clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cidade.getId());
		clienteForm.setDataNascimento(cliente.getDataNascimento());
		clienteForm.setSexo(cliente.getSexo());
		clienteForm.setNomeCompleto(cliente.getNomeCompleto());
	
		clienteDto = new ClienteDto();
		clienteDto.setDataNascimento(cliente.getDataNascimento());
		clienteDto.setId(cliente.getId());
		clienteDto.setIdade(cliente.getIdade());
		clienteDto.setIdCidade(cidade.getId());
		clienteDto.setNomeCompleto(cliente.getNomeCompleto());
		clienteDto.setSexo(cliente.getSexo());
	}

	/**
	 * Testa a criação de um cliente com informações válidas.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteValido() throws Exception {
		when(clienteMapper.clienteFormToCliente(any())).thenReturn(cliente);
		when(clienteService.salvaCliente(cliente)).thenReturn(cliente);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isCreated());
	}
	
	/**
	 * Testa a criação de um cliente com cidade inválida - ID da cidade
	 * não corresponde a nenhuma cidade cadastrada no sistema.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteComCidadeNaoExistente() throws Exception {
		when(clienteMapper.clienteFormToCliente(any())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Testa a criação de um cliente com mascimento no futuro.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteComNascimentoNoFuturo() throws Exception {
		clienteForm.setDataNascimento(LocalDate.now().plusYears(5));
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a criação de um cliente com nome em branco.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteComNomeEmBranco() throws Exception {
		clienteForm.setNomeCompleto("     ");
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a criação de um cliente com tamanho do nome maior
	 * que o máximo de caracteres permitidos para o campo.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteComNomeMaiorQueMaximo() throws Exception {
		clienteForm.setNomeCompleto(StringUtils.repeat("*", 100));
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a criação de um cliente com sexo nulo.
	 * 
	 * @throws Exception
	 */
	@Test
	void testSalvaClienteComSexoNulo() throws Exception {
		clienteForm.setSexo(null);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a busca por id de um cliente.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaClientePorId() throws Exception {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clienteService.buscaPorId(cliente.getId())).thenReturn(cliente);
		mvc.perform(MockMvcRequestBuilders.get("/clientes?id=" + cliente.getId()))		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/**
	 * Testa a busca por id de um cliente que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaClienteNaoExistentePorId() throws Exception {
		when(clienteService.buscaPorId(cliente.getId())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.get("/clientes?id=" + cliente.getId()))		
				.andExpect(status().isNotFound());
	}

	/**
	 * Testa a busca por id de um cliente.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaClientePorNome() throws Exception {
		when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);
		when(clienteService.buscaPorNome(cliente.getNomeCompleto())).thenReturn(Arrays.asList(cliente));
		mvc.perform(MockMvcRequestBuilders.get("/clientes?nome=" + cliente.getNomeCompleto()))		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/**
	 * Testa a busca por id de um cliente que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaClienteNaoExistentePorNome() throws Exception {
		when(clienteService.buscaPorNome(cliente.getNomeCompleto())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.get("/clientes?nome=" + cliente.getNomeCompleto()))		
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}

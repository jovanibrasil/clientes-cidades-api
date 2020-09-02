package br.com.compasso.clientes.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

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

import br.com.compasso.clientes.ScenarioFactory;
import br.com.compasso.clientes.dominio.Cliente;
import br.com.compasso.clientes.dominio.dto.ClienteDTO;
import br.com.compasso.clientes.dominio.form.AtualizacaoClienteForm;
import br.com.compasso.clientes.dominio.form.ClienteForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.service.ClienteService;
import br.com.compasso.clientes.util.DateUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteControllerTest {
	
	@MockBean
	private ClienteService clienteService;
	
	@Autowired
	private MockMvc mvc;
	
	private ClienteForm clienteForm;
	private ClienteDTO clienteDto;
	private AtualizacaoClienteForm atualizaClienteForm;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		cliente = ScenarioFactory.criaClienteJoao();
		clienteForm = ScenarioFactory.criaClienteFormJoao();
		clienteDto = ScenarioFactory.criaClienteDTOJoao();
		atualizaClienteForm = ScenarioFactory.criaClienteFormAlteracaoJoao();
	}

	/**
	 * Testa a criação de um cliente com informações válidas.
	 * 
	 * @throws Exception
	 */
	@Test
	void salvaCliente_QuandoClienteValido_EsperaIsCreated() throws Exception {
		when(clienteService.salvaCliente(any())).thenReturn(clienteDto);
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
	void salvaCliente_QuandoCidadeNaoExistente_EsperaBadRequest() throws Exception {
		when(clienteService.salvaCliente(any())).thenThrow(InvalidParameterException.class);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a criação de um cliente com mascimento no futuro.
	 * 
	 * @throws Exception
	 */
	@Test
	void salvaCliente_QuandoNascimentoNoFuturo_EsperaBadRequest() throws Exception {
		clienteForm.setDataNascimento(DateUtils.converteLocalDateToString(LocalDate.now().plusYears(5)));
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
	void salvaCliente_QuandoNomeEmBranco_EsperaBadRequest() throws Exception {
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
	void salvaCliente_QuandoTamanhoNomeMaiorQueMaximo_EsperaBadRequest() throws Exception {
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
	void salvaCliente_QuandoSexoNulo_EsperaBadRequest() throws Exception {
		clienteForm.setSexo(null);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(clienteForm)))		
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Testa a busca de um cliente por id.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaClientePorId_QuandoClienteExiste_EsperaOkBodyNaoVazio() throws Exception {
		when(clienteService.buscaPorId(cliente.getId())).thenReturn(clienteDto);
		mvc.perform(MockMvcRequestBuilders.get("/clientes/" + cliente.getId()))		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/**
	 * Testa a busca de um cliente por id que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaClientePorId_QuandoClienteNaoExisteComId_EsperaNotFound() throws Exception {
		when(clienteService.buscaPorId(cliente.getId())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.get("/clientes/" + cliente.getId()))		
				.andExpect(status().isNotFound());
	}

	/**
	 * Testa a busca de um cliente por id.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaClientePorNome_QuandoClienteExiste_EsperaOkBodyNaoVazio() throws Exception {
		when(clienteService.buscaPorNome(cliente.getNomeCompleto())).thenReturn(Arrays.asList(clienteDto));
		mvc.perform(MockMvcRequestBuilders.get("/clientes?nome=" + cliente.getNomeCompleto()))		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	

	/**
	 * Testa a busca de cliente sem parâmetros.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCliente_QuandoSemParametros_EsperaBadRequest() throws Exception {
		when(clienteService.buscaPorNome(cliente.getNomeCompleto())).thenReturn(Arrays.asList(clienteDto));
		mvc.perform(MockMvcRequestBuilders.get("/clientes"))		
				.andExpect(status().isBadRequest());
	}
	
	
	/**
	 * Testa a busca de um cliente por id que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaClientePorId_QuandoClienteNaoExiste_EsperaRetornoNotFound() throws Exception {
		when(clienteService.buscaPorNome(cliente.getNomeCompleto())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.get("/clientes?nome=" + cliente.getNomeCompleto()))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Testa delete por id de um cliente que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void removeCliente_QuandoClienteNaoExiste_EsperaNotFound() throws Exception {
		doThrow(NotFoundException.class).when(clienteService).removeCliente(-1L);
		mvc.perform(MockMvcRequestBuilders.delete("/clientes/-1"))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Testa delete por id de um cliente que não existe.
	 * 
	 * @throws Exception
	 */
	@Test
	void removeCliente_QuandoClienteExiste_EsperaNoContent() throws Exception {
		doNothing().when(clienteService).removeCliente(cliente.getId());
		mvc.perform(MockMvcRequestBuilders.delete("/clientes/"+cliente.getId()))		
				.andExpect(status().isNoContent());
	}
	
	/**
	 * Atualiza o nome de um cliente.
	 * 
	 * @throws Exception
	 */
	@Test
	void atualizaCliente_QuandoNomeValido_EsperaOkBodyNaoVazio() throws Exception {
		when(clienteService.alteraCliente(any())).thenReturn(clienteDto);
		mvc.perform(MockMvcRequestBuilders.patch("/clientes/" + cliente.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(atualizaClienteForm)))		
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	/**
	 * Tenta atualizar o nome de um cliente não existente.
	 * 
	 * @throws Exception
	 */
	@Test
	void atualizaCliente_QuandoClienteNaoExistente_EsperaNotFound() throws Exception {
		when(clienteService.alteraCliente(any())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.patch("/clientes/-1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(atualizaClienteForm)))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Tenta atualizar o nome de um cliente mas o nome é inválido.
	 * 
	 * @throws Exception
	 */
	@Test
	void atualizaCliente_QuandoComNomeInvalido_EsperaBadRequest() throws Exception {
		atualizaClienteForm.setNomeCompleto("");
		mvc.perform(MockMvcRequestBuilders.patch("/clientes/" + cliente.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(atualizaClienteForm)))		
				.andExpect(status().isBadRequest());
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

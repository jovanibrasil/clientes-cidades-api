package br.com.compasso.clientes.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
import br.com.compasso.clientes.dominio.dto.CidadeDTO;
import br.com.compasso.clientes.dominio.form.CidadeForm;
import br.com.compasso.clientes.exception.InvalidParameterException;
import br.com.compasso.clientes.exception.NotFoundException;
import br.com.compasso.clientes.service.CidadeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CidadeControllerTest {

	@MockBean
	private CidadeService cidadeService;
	@Autowired
	private MockMvc mvc;
	
	private CidadeForm cidadeForm;
	private CidadeDTO cidadeDto;
	
	@BeforeEach
	void setUp() throws Exception {
		cidadeForm = ScenarioFactory.criaCidadeFormPoa();
		cidadeDto = ScenarioFactory.criaCidadeDtoPoa();
	}

	/**
	 * Testa a criação de uma cidade com informações válidas.
	 * 
	 * @throws Exception
	 */
	@Test
	void criaCidade_QuandoCidadeValida_EsperaCreated() throws Exception {
		when(cidadeService.salvaCidade(any())).thenReturn(cidadeDto);
		mvc.perform(MockMvcRequestBuilders.post("/cidades")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(cidadeForm)))		
				.andExpect(status().isCreated());
	}
	
	/**
	 * Testa a criação de uma cidade com estado inválido.
	 * 
	 * @throws Exception
	 */
	@Test
	void criaCidade_QuandoComEstadoInvalido_EsperaNotFound() throws Exception {
		when(cidadeService.salvaCidade(any())).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.post("/cidades")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(cidadeForm)))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Testa a criação de uma cidade que já existe em um estado.
	 * 
	 * @throws Exception
	 */
	@Test
	void criaCidade_QuandoJaExistente_EsperaBadRequest() throws Exception {
		when(cidadeService.salvaCidade(any())).thenThrow(InvalidParameterException.class);
		mvc.perform(MockMvcRequestBuilders.post("/cidades")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(cidadeForm)))		
				.andExpect(status().isBadRequest());
	}

	/**
	 * Testa busca de cidade sem parâmetros.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCidade_QuandoSemParametros_EsperaOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/cidades"))		
				.andExpect(status().isOk());
	}
	
	/**
	 * Testa busca de cidade por nome.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCidade_QuandoExisteComNome_EsperaOk() throws Exception {
		when(cidadeService.buscaPorNomeCidadeESiglaEstado(cidadeForm.getNome(), "")).thenReturn(Arrays.asList(cidadeDto));
		mvc.perform(MockMvcRequestBuilders.get("/cidades?nome=" + cidadeForm.getNome())
				.contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isOk());
	}
	
	/**
	 * TEsta busca de cidade inexistente por nome.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCidade_QuandoNaoExistentePorNome_EsperaNotFound() throws Exception {
		when(cidadeService.buscaPorNomeCidadeESiglaEstado(cidadeForm.getNome(), "")).thenThrow(NotFoundException.class);
		mvc.perform(MockMvcRequestBuilders.get("/cidades/" + cidadeForm.getNome())
				.contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isNotFound());
	}
	
	/**
	 * Testa busca de cidade por estado.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCidade_QuandoExistePorEstado_EsperaOk() throws Exception {
		String sigla = cidadeForm.getEstadoSigla();
		when(cidadeService.buscaPorNomeCidadeESiglaEstado("", sigla)).thenReturn(Arrays.asList(cidadeDto));
		mvc.perform(MockMvcRequestBuilders.get("/cidades?estadoSigla=" + sigla)
				.contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isOk());
	}
	
	/**
	 * Testa busca de cidade por estado inválido.
	 * 
	 * @throws Exception
	 */
	@Test
	void buscaCidade_QuandoEstadoInvalido_EsperaOk() throws Exception {
		String sigla = "??";
		when(cidadeService.buscaPorNomeCidadeESiglaEstado("", sigla)).thenReturn(Arrays.asList());
		mvc.perform(MockMvcRequestBuilders.get("/cidades?estadoSigla=" + sigla)
				.contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isOk());
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

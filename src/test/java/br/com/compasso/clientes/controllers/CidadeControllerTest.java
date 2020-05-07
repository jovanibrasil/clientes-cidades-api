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

import br.com.compasso.clientes.dtos.CidadeDto;
import br.com.compasso.clientes.exceptions.InvalidParameterException;
import br.com.compasso.clientes.exceptions.NotFoundException;
import br.com.compasso.clientes.forms.CidadeForm;
import br.com.compasso.clientes.mappers.CidadeMapper;
import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.services.CidadeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CidadeControllerTest {

	@MockBean
	private CidadeService cidadeService;
	@MockBean
	private CidadeMapper cidadeMapper;
	@Autowired
	private MockMvc mvc;
	
	private CidadeForm cidadeForm;
	private CidadeDto cidadeDto;
	private Cidade cidade;
	private Estado estado;
	
	@BeforeEach
	void setUp() throws Exception {
		estado = new Estado(1L, "RS");;
		cidade = new Cidade(1L, "Porto Alegre", estado);
		
		cidadeForm = new CidadeForm();
		cidadeForm.setNome(cidade.getNome());
		cidadeForm.setEstadoSigla(cidade.getEstado().getSigla());
	
		cidadeDto = new CidadeDto();
		cidadeDto.setId(cidade.getId());
		cidadeDto.setNome(cidade.getNome());
	}

	/**
	 * Testa a criação de uma cidade com informações válidas.
	 * 
	 * @throws Exception
	 */
	@Test
	void testCriaCidadeValida() throws Exception {
		when(cidadeMapper.cidadeFormToCidade(any())).thenReturn(cidade);
		when(cidadeService.salvaCidade(cidade)).thenReturn(cidade);
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
	void testCriaCidadeEstadoInvalido() throws Exception {
		when(cidadeMapper.cidadeFormToCidade(any())).thenThrow(NotFoundException.class);
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
	void testCriaCidadeJaExistente() throws Exception {
		when(cidadeMapper.cidadeFormToCidade(any())).thenReturn(cidade);
		when(cidadeService.salvaCidade(cidade)).thenThrow(InvalidParameterException.class);
		mvc.perform(MockMvcRequestBuilders.post("/cidades")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(cidadeForm)))		
				.andExpect(status().isBadRequest());
	}

	/**
	 * Testa busca de cidade por nome.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaCidadePorNome() throws Exception {
		when(cidadeService.buscaPorNome(cidade.getNome())).thenReturn(Arrays.asList(cidade));
		when(cidadeMapper.cidadeToCidadeDto(cidade)).thenReturn(cidadeDto);
		mvc.perform(MockMvcRequestBuilders.get("/cidades?nome=" + cidade.getNome())
				.contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isOk());
	}
	
	/**
	 * TEsta busca de cidade inexistente por nome.
	 * 
	 * @throws Exception
	 */
	@Test
	void testBuscaCidadeNaoExistentePorNome() throws Exception {
		when(cidadeService.buscaPorNome(cidade.getNome())).thenThrow(NotFoundException.class);
		when(cidadeMapper.cidadeToCidadeDto(cidade)).thenReturn(cidadeDto);
		mvc.perform(MockMvcRequestBuilders.get("/cidades/" + cidade.getNome())
				.contentType(MediaType.APPLICATION_JSON))		
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

package br.com.compasso.clientes;

import java.time.LocalDate;

import br.com.compasso.clientes.modelos.Cidade;
import br.com.compasso.clientes.modelos.Cliente;
import br.com.compasso.clientes.modelos.Estado;
import br.com.compasso.clientes.modelos.dtos.CidadeDTO;
import br.com.compasso.clientes.modelos.dtos.ClienteDTO;
import br.com.compasso.clientes.modelos.enums.Sexo;
import br.com.compasso.clientes.modelos.forms.AtualizacaoClienteForm;
import br.com.compasso.clientes.modelos.forms.CidadeForm;
import br.com.compasso.clientes.modelos.forms.ClienteForm;

public class ScenarioFactory {
	
	private static final String CIDADE_POA_NOME = "Porto Alegre";
	private static final String CIDADE_SJ_NOME = "São Jerônimo";
	private static final String ESTADO_RS_SIGLA = "RS";
	private static final String NOME_POS_UPDATE = "João Silva";
	
	public static Estado criaEstadoRS() {
		return new Estado(1L, ESTADO_RS_SIGLA);
	}
	
	public static Estado criaEstadoRSIdNull() {
		return new Estado(null, ESTADO_RS_SIGLA);
	}
	
	public static Cidade criaCidadePoaIdNull(){
		return new Cidade(null, CIDADE_POA_NOME, criaEstadoRS());
	}

	public static Cidade criaCidadePoaIdNull(Estado estado){
		return new Cidade(null, CIDADE_POA_NOME, estado);
	}
	
	public static Cidade criaCidadeSjIdNull() {
		return new Cidade(null, CIDADE_SJ_NOME, criaEstadoRS());
	}
	
	public static Cidade criaCidadePoa(){
		return new Cidade(1L, CIDADE_POA_NOME, criaEstadoRS());
	}
	
	public static CidadeDTO criaCidadeDtoPoa(){
		Cidade cidade = criaCidadePoa();
		CidadeDTO cidadeDto = new CidadeDTO();
		cidadeDto.setId(cidade.getId());
		cidadeDto.setNome(cidade.getNome());
		return cidadeDto;
	}
	
	public static CidadeForm criaCidadeFormPoa(){
		Cidade cidade = criaCidadePoa();
		CidadeForm cidadeForm = new CidadeForm();
		cidadeForm.setNome(cidade.getNome());
		cidadeForm.setEstadoSigla(cidade.getEstado().getSigla());
		return cidadeForm;
	}

	public static Cliente criaClienteJoao() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setCidade(criaCidadePoa());
		cliente.setNomeCompleto("João Silva");
		cliente.setSexo(Sexo.M);
		cliente.setDataNascimento(LocalDate.now().minusYears(30L));
		return cliente;
	}

	public static Cliente criaCliente(String nome, Cidade cidade) {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setCidade(cidade);
		cliente.setNomeCompleto(nome);
		cliente.setSexo(Sexo.M);
		cliente.setDataNascimento(LocalDate.now().minusYears(30L));
		return cliente;
	}
	
	public static AtualizacaoClienteForm criaClienteFormAlteracaoJoao() {
		AtualizacaoClienteForm atCliForm = new AtualizacaoClienteForm();
		atCliForm.setNomeCompleto(NOME_POS_UPDATE);
		return atCliForm;
	}
	
	public static Cliente criaClienteAlteradoJoao() {
		Cliente cliente = criaClienteJoao();
		cliente.setNomeCompleto(NOME_POS_UPDATE);
		return cliente;
	}		

	public static ClienteDTO criaClienteDTOJoao() {
		Cliente cliente = criaClienteJoao();
		ClienteDTO clienteDto = new ClienteDTO();
		clienteDto.setDataNascimento(cliente.getDataNascimento());
		clienteDto.setId(cliente.getId());
		clienteDto.setIdade(cliente.getIdade());
		clienteDto.setIdCidade(cliente.getCidade().getId());
		clienteDto.setNomeCompleto(cliente.getNomeCompleto());
		clienteDto.setSexo(cliente.getSexo());
		return clienteDto;
	}

	public static ClienteForm criaClienteFormJoao() {
		Cliente cliente = criaClienteJoao();
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setCidadeId(cliente.getCidade().getId());
		clienteForm.setDataNascimento(cliente.getDataNascimento());
		clienteForm.setSexo(cliente.getSexo());
		clienteForm.setNomeCompleto(cliente.getNomeCompleto());
		return clienteForm;
	}
	
	public static Cliente criaClienteMaria() {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente();
		cliente.setCidade(criaCidadePoa());
		cliente.setDataNascimento(LocalDate.now().minusYears(15));
		cliente.setNomeCompleto("Maria Silva");
		cliente.setSexo(Sexo.F);
		return cliente;
	}

	public static Cliente criaClienteJoao(Cidade cidade) {
		Cliente joao = criaClienteJoao();
		joao.setCidade(cidade);
		return joao;
	}
	
}

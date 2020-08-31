# language: pt
Funcionalidade: Cadastrar cliente

	Eu, como usuário
	Desejo cadastrar um cliente no sistema. 
	
	Cenário: Usuário faz uma chamada POST /clientes
		Dado que existe uma cidade cadastrada 
		Quando é feito um POST para "/clientes" com o cliente no corpo
		| nomeCompleto 	| dataNascimento 	| sexo | cidadeId |
		| Jovani Brasil | 13/06/1992			| M		 | {idCidadeCadasreada} |
		Então é retornado código 201 como resultado da operação
		E possui o endereço do recurso no cabeçalho

	Cenário: Cadastrar um cliente referenciando cidade não existente
		Dado que não existe uma cidade com id 0
		Quando é feito um POST para "/clientes" com o cliente no corpo
		| nomeCompleto 	| dataNascimento 	| sexo | cidadeId |
		| Jovani Brasil | 13/06/1992			| M		 | 0				|
		Então é retornado código 400 como resultado da operação
		E o corpo da mensagem contém informações de erro		

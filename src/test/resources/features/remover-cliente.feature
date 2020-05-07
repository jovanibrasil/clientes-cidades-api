# language: pt
Funcionalidade: Remoção de cliente

	Eu, como usuário
	Desejo remover um cliente que foi cadastrado no sistema. 

	Cenário: Remover um cliente que existe no sistema
		Dado que existe uma cidade cadastrada no sistema com id 8
		E é feito um POST para "/clientes" com o cliente no corpo
		| nomeCompleto 	| dataNascimento 	| sexo | cidadeId |
		| Jovani Brasil | 13/06/1992			| M		 | 8				|
		Quando é feito um DELETE para "/clientes/{id}" passando o id desta cidade
		Então é retornado código 204 como resultado da operação
		E o corpo da mensagem é vazio

	Cenário: Remover um cliente que não existe
		Dado que não existe um cliente com id 0
		Quando é feito um DELETE para "/clientes/0" passando o id desta cidade
		Então é retornado código 404 como resultado da operação
		E o corpo da mensagem contém informações de erro
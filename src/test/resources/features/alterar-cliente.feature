# language: pt
Funcionalidade: Alteração de cliente

	Eu, como usuário
	Desejo alterar o nome de um cliente cadastrado no sistema. 

	Cenário: Alterar um cliente que existe no sistema
		Dado que existe uma cidade cadastrada no sistema com id 8
		E é feito um POST para "/clientes" com o cliente no corpo
		| nomeCompleto 	| dataNascimento 	| sexo | cidadeId |
		| Jovani | 13/06/1992			| M		 | 8				|
		Quando é feito um PATCH para "/clientes/{id}" passando o id desta cidade
		| nomeCompleto |
		| Jovani Brasil |
		Então é retornado código 200 como resultado da operação
		E o corpo da mensagem não é vazio

	Cenário: Tentar alterar um cliente que não existe
		Dado que não existe um cliente com id 0
		Quando é feito um PATCH para "/clientes/0" passando o id desta cidade
		| nomeCompleto |
		| Jovani Brasil |
		Então é retornado código 404 como resultado da operação
		E o corpo da mensagem contém informações de erro
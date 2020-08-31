# language: pt
Funcionalidade: Cadastrar cidade

	Eu, como usuário
	Desejo criar uma cidade no sistema
	
	Cenário: Cadastrar uma cidade no sistema
		Dado que existe um estado "RS" pré-cadastrado sem cidades
		Quando é feito um POST para "/cidades" com a cidade no corpo 
			| nomeCidade 		| estadoSigla |
			| Porto Alegre	| RS 					|
		Então é retornado código 201 como resultado da operação
		
	Cenário: Cadastrar uma cidade com estado inválido no sistema
		Dado que não existe um estado "XX" cadastrado
		Quando é feito um POST para "/cidades" com a cidade no corpo 
			| nomeCidade 		| estadoSigla |
			| Porto Alegre	| XX 					|
		Então é retornado código 400 como resultado da operação
		E o corpo da mensagem contém informações de erro

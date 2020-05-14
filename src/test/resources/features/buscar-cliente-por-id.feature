# language: pt
Funcionalidade: Buscar cliente por id

	Eu, como usuário
	Desejo buscar um cliente por id no sistema
	
	Cenário: Busca por um cliente existente
		Dado que existe um cliente cadastrado no sistema 
		Quando é feito um GET para "/clientes/" com o id do cliente
		Então é retornado código 200 como resultado da operação
		
		
	Cenário: Busca por id um cliente que não existe
		Dado que não existe um cliente com id 0
		Quando é feito um GET para "/clientes/" com variável "0"
		Então é retornado código 404 como resultado da operação
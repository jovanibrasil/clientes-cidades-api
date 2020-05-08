# language: pt
Funcionalidade: Buscar cliente por id

	Eu, como usuário
	Desejo buscar um cliente por id no sistema
	
	Cenário: Busca por um cliente existente
		Quando é feito um GET para "/clientes/" com variável "10"
		Então é retornado código 200 como resultado da operação
		
		
	Cenário: Busca por id um cliente que não existe
		Quando é feito um GET para "/clientes/" com variável "0"
		Então é retornado código 404 como resultado da operação
# language: pt
Funcionalidade: Buscar cliente por nome

	Eu, como usuário
	Desejo buscar um cliente por nome no sistema
	
	Cenário: Busca por um cliente que foi cadastrado
		Dado que existe um cliente cadastrado chamado "Pedro"
		Quando é feito um GET para "/clientes" com parâmetro "nome" com valor "Pedro"
		Então é retornado código 200 como resultado da operação
		E o corpo da mensagem contém ao menos um objeto
		
		
	Cenário: Busca por nome um cliente que não existe
		Dado que não existe um cliente chamado "Rosane" cadastrado
		Quando é feito um GET para "/clientes" com parâmetro "nome" com valor "Rosane"
		Então é retornado código 200 como resultado da operação
		E o corpo da mensagem não contém objetos
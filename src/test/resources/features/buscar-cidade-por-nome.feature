# language: pt
Funcionalidade: Buscar cidade por nome

	Eu, como usuário
	Desejo buscar uma cidade por nome no sistema
	
	Cenário: Busca por uma cidade que foi cadastrada
		Dado que existe uma cidade "Bom Jesus" cadastrada
		Quando é feito um GET para "/cidades" com parâmetro "nome" com valor "Bom Jesus"
		Então é retornado código 200 como resultado da operação
		E o corpo da mensagem contém ao menos um objeto
		
		
	Cenário: Busca por nome uma cidade que não existe
		Dado que não existe uma cidade "Triunfo" cadastrada
		Quando é feito um GET para "/cidades" com parâmetro "nome" com valor "Triunfo"
		Então é retornado código 200 como resultado da operação
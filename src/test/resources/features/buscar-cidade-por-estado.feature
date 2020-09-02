# language: pt
Funcionalidade: Buscar cidade por estado

	Eu, como usuário
	Desejo buscar uma cidade por nome no sistema
	
	Cenário: Busca por uma cidade que foi cadastrada
		Dado que existe ao menos uma cidade no estado "RS"
		Quando é feito um GET para "/cidades" com parâmetro "estadoSigla" com valor "RS"
		Então é retornado código 200 como resultado da operação
		E o corpo da mensagem contém ao menos um objeto
		
		
	Cenário: Busca por nome uma cidade que não existe
		Dado que não existe uma cidade no estado "AM"
		Quando é feito um GET para "/cidades" com parâmetro "estadoSigla" com valor "AM" 
		Então é retornado código 200 como resultado da operação
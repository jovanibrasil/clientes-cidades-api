# language: pt
Funcionalidade: Criar cidade

	Eu, como usuário
	Desejo criar uma cidade no sistema
	
	Cenário: Cliente faz uma chamada POST /cidades
		Quando o cliente faz um POST para /cidades com a cidade no corpo da mensagem
		Então o cliente recebe código de retorno 201
		E o corpo da mensagem é vazio
		E possui o endereço do recurso no cabeçalho
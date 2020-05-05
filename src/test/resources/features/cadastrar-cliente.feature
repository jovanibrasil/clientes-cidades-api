# language: pt
Funcionalidade: Criar cliente

	Eu, como usuário
	Desejo cadastrar um cliente no sistema. 
	
	Cenário: Usuário faz uma chamada POST /clientes
		Quando o usuário faz um POST para /clientes com as informações do cliente no corpo da mensagem
		Então o cliente recebe código de retorno 201
		E o corpo da mensagem é vazio
		E possui o endereço do recurso no cabeçalho
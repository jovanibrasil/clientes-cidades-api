# language: pt
Funcionalidade: Remoção de cliente

	Eu, como usuário
	Desejo remover um cliente que foi cadastrado no sistema. 

	Cenário: Remover um cliente que existe no sistema
		Dado que existe um cliente cadastrado
		Quando é feito um DELETE para "/clientes/" passando o id deste cliente
		Então é retornado código 204 como resultado da operação

	Cenário: Remover um cliente que não existe
		Dado que não existe um cliente com id 0
		Quando é feito um DELETE para "/clientes/" passando o id 0
		Então é retornado código 404 como resultado da operação
		E o corpo da mensagem contém informações de erro
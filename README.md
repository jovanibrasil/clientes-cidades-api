# Roteiro Microservice Spring Boot

## Instruções de execução

Para executar a aplicação basta executar o comando ```mvn spring-boot:run```. Este comando executá a aplicação no perfil dev usando o banco em memória H2, sendo suficiente para teste da aplicação. Se você preferir pode executar no perfil stage, onde o objetivo é executar com uma configuração próxima a de produção. Primeiro você precisará ter um Mysql devidamente configurado para só então executar o comando ```mvn spring-boot:run -Pstage```. [Neste diretório](/mysql-docker) você encontra um Mysql containerizado (dockerizado) que você pode executar utilizando os comandos que se encontram no Makefile do diretório. 

Se você quiser executar os testes basta executar o comando ```mvn test -Ptest```. Serão executados todos os testes unitários (Junit e Mockito) e de integração (Cucumber). 

A documentação Swagger da API pode ser conferida [aqui](http://localhost:8080/swagger-ui.html) se você estiver rodando localmente a aplicação neste momento. E por comodidade em breve ela também estará disponível online. 

Qualquer dúvida estou a disposição.

## Objetivo

Nosso objetivo com este passo do processo é conhecer melhor as suas habilidades técnicas.

Conhecendo você melhor, poderemos selecionar quais desafios já podemos passar para você e quais precisaremos preparar você melhor para enfrentá-los.

## Requisitos da entrega

Nesta estapa esperamos que você construa o código que contemple as seguintes operações expostas como endpoints REST para:

* Cadastrar cidade
* Cadastrar cliente
* Consultar cidade pelo nome
* Consultar cidade pelo estado
* Consultar cliente pelo nome
* Consultar cliente pelo Id
* Remover cliente
* Alterar o nome do cliente

Considere o cadastro com dados básicos: 
* Cidades: nome e estado
* Cliente: nome completo, sexo, data de nascimento, idade e cidade onde mora.

## Cenário

No nosso dia-a-dia trabalhamos com o desenvolvimento de microserviços desenvolvidos utilizando Spring Boot. Buscamos automação dos processos de garantia da qualidade, testes, deployment e release.

## Critérios


### Análise

A análise pelo padrinho será feita da seguinte forma:

1) Vamos analisar e compilar o seu código;
2) Rodar sua aplicação e executar testes para validar o atendimento funcional dos items acima;
3) Verificar se o seu código é limpo (Clean Code), fácil de entender e de dar manutenção;
4) Simularemos uma revisão do seu código, percorremos o código junto com você para discutirmos sobre suas decisões de implementação, os pontos positivos e negativos;


Requisitos Obrigatórios:
* Operações acima funcionando sem erros
* Código válido, estruturado e organizado para que possamos testar sua aplicação
* Testes unitários e de integração

Utilização de Java11, spring boot, maven, suporte às IDEs IntelliJ e Eclipse o resto é por sua conta escolher.

Dicas:
* Procure fazer uma entrega simples mas consistente, usando a experiência e conhecimento adquiridos durante sua carreira;
* Não se preocupe em entregar algo extremamente completo ou rebuscado;
* Dê o seu melhor;

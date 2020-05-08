DELETE FROM clientes;
DELETE FROM cidades;
DELETE FROM estados;

INSERT INTO estados (estado_id, sigla) values (1, 'RS');
INSERT INTO estados (estado_id, sigla) values (2, 'SC');
INSERT INTO estados (estado_id, sigla) values (3, 'SP');
INSERT INTO estados (estado_id, sigla) values (4, 'AM');
INSERT INTO estados (estado_id, sigla) values (5, 'BA');
INSERT INTO estados (estado_id, sigla) values (6, 'AC');

INSERT INTO cidades (cidade_id, nome, estado_id) values (7, 'Alegrete', 1);
INSERT INTO cidades (cidade_id, nome, estado_id) values (8, 'Bom Jesus', 1);
INSERT INTO cidades (cidade_id, nome, estado_id) values (9, 'Bom Jesus', 2);

INSERT INTO clientes (cliente_id, nome_completo, sexo, data_nascimento, cidade_id) 
	values (10, 'João', 'M', '1990-06-12', 8);
	
INSERT INTO clientes (cliente_id, nome_completo, sexo, data_nascimento, cidade_id)
	values (11, 'Pedro', 'M', '1992-06-12', 9);
	
	
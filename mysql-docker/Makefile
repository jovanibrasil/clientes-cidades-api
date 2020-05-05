stop:
	- docker stop mysql-database
clean: stop
	- docker rm mysql-database
clean-data:  clean
	- docker volume rm mysql-data
create-data: clean-data
	docker volume create mysql-data
build:
	docker build -t mysql-database .
run:
	docker run -p 3306:3306 -p 33060:33060 --name=mysql-database --mount source=mysql-data,target=/var/lib/mysql --network net -d mysql-database
fresh-run: create-data build run

start:
	docker start mysql-database 
bash:
	docker container exec -i -t mysql-database bash

# mysql -u root -p  # entrar no mysql como root
# show databases # mostra bancos criados

# devJavaSpringSenior
Desafio Backend

Neste desafio você deverá implementar uma API REST para um sistema simples de
contas a pagar. O sistema permitirá realizar o CRUD de uma conta a pagar, alterar a
situação dela quando for efetuado pagamento, obter informações sobre as contas
cadastradas no banco de dados, e importar um lote de contas de um arquivo CSV, conforme
descrito abaixo.

Requisitos Gerais
1. Utilizar a linguagem de programação Java, versão 17 ou superior.
2. Utilizar Spring Boot.
3. Utilizar o banco de dados PostgreSQL.
4. A aplicação deve ser executada em um container Docker.
5. Tanto a aplicação, banco de dados, quanto outros serviços necessários para
executar a aplicação, devem ser orquestrados utilizando Docker Compose.
6. O código do projeto deve ser hospedado no GitHub ou GitLab.
7. Utilizar mecanismo de autenticação.
8. Organizar o projeto com Domain Driven Design.
9. Utilizar o Flyway para criar a estrutura de banco de dados.
10. Utilizar JPA.
11. Todas as APIs de consulta devem ser paginadas.

Requisitos Específicos
1. Cadastrar a tabela no banco de dados para armazenar as contas a pagar. Deve
incluir no mínimo os seguintes campos: (Faça a tipagem conforme achar adequado)
a. id
b. data_vencimento
c. data_pagamento
d. valor
e. descricao
f. situacao
2. Implementar a entidade “Conta” na aplicação, de acordo com a tabela criada
anteriormente.
3. Implementar as seguintes APIs:

a. Cadastrar conta;
b. Atualizar conta;
c. Alterar a situação da conta;
d. Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;
e. Obter conta filtrando o id;
f. Obter valor total pago por período.
4. Implementar mecanismo para importação de contas a pagar via arquivo csv.
a. O arquivo será consumido via API.
5. Implementar testes unitários.

*Após baixar o projeto, executar o comando docker-compose up --build de dentro do diretório onde o arquivo docker-compose está localizado, para criar a imagem da aplicação e subir os containers.
Criei o usuário e senha abaixo para autenticar e obter o token.

Após adicionar o token, já conseguirá acessar as rotas. Todas estão funcionando.

* Sobre o requisito geral número 11, não foi possível paginar TODAS as rotas, pois a paginação depende de uma lista para ser implementada, ou seja, não consigo paginar uma rota que traz apenas 1 registro.

![image](https://github.com/user-attachments/assets/81f1779c-5ecc-43a9-898a-c911bc018083)

Cadastrar Conta:
![image](https://github.com/user-attachments/assets/9d638471-b325-421f-92c0-0873d169ecfc)

Atualizar conta:
![image](https://github.com/user-attachments/assets/768c174c-5366-4579-91b7-faf3176c27b6)

Atualizar Siuação de Conta:
![image](https://github.com/user-attachments/assets/1fdb190a-f39e-42b3-85c5-22312ec5a5e2)

Lista de Contas por Vencimento e descrição:

![image](https://github.com/user-attachments/assets/9c400bd4-8a3e-48fb-b3d4-43a5dcd63435)

Conta por ID: 
![image](https://github.com/user-attachments/assets/e4c99526-0897-4354-8ec7-3c7c622604f6)

Total pago por período:
![image](https://github.com/user-attachments/assets/3076dd40-6d10-4f3e-8aee-bee8d481ffaf)

Importar contas via arquivo .csv:
![image](https://github.com/user-attachments/assets/baa092d3-6fc8-4076-85b8-e02172076272)

Desde já agradeço pela oportunidade e espero que dê tudo certo!
Muito obrigado!









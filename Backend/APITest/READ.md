#  Automação de Testes API - DemoQA

Bem-vindo à minha resolução do desafio de testes de API!
Imagine que este código é um "robô assistente" que entra no site da livraria DemoQA, cria um usuário, aluga livros e depois limpa tudo sozinho, sem que ninguém precise clicar em nada.

---

##  Guia de uso (Passo a Passo)

Siga estas "fases" para fazer o robô assistente funcionar:

### Fase 1: O que você precisa instalado:
1. Java (JDK)
2. VS Code
3. Maven 

#### Fase 2: Como Rodar

Existem duas formas de dar o "Play":

##### Opção A: via Terminal
1. No seu VS Code, clique em **Terminal**.
2. Digite cd APITest para ir ao diretorio alvo,
3. Digite este comando e tecle enter:
   mvn clean install (aguarde o build finalizar)
   mvn test (aguarde o teste finalizar e o resultado deve ser - Tests run: 3, Failures: 0, Errors: 0, Skipped: 0)

    Opção B: Ao lado de cada @Test existe um botão com simbolo de play, ao clicar nesse botão o teste será executado. 
    Porém nessa modalidade manual, você deverá executar os testes na ordem 1, 2 e 3 para que tudo funcione como esperado.




*** Observação ***

Durante os testes manuais utilizando o Postman, foi encontrado um comportamento diferente do esperado para o método DELETE de user.
Quando eu faço o DELETE do mesmo user 2x ou mais, o status code recebido é 200, porém a mensagem de erro não retrata o mesmo comportamento:

"User id not correct!"

Sugestão de melhoria para esse status, utilizar um status code 400 bad request ou 404 user id not found.
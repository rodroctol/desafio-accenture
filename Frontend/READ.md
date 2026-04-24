# Projeto de Automação Frontend - DemoQA

Este é um projeto que contém a automação de testes do site DemoQA. O objetivo é validar o comportamento da interface do usuário.

## O que está sendo testado?
A automação cobre os seguintes cenários de teste:

Fluxo Completo de Registro (CRUD):

Criação de um novo registro com preenchimento de formulário modal.

Edição de um registro existente para validar a atualização de dados na tabela.

Exclusão de um registro e verificação de sua remoção da interface.

Validação de Paginação e Volume de Dados:

Criação massiva de registros (12 registros simultâneos).

Alteração dinâmica do seletor de linhas por página (Rows per page) para 20 linhas.

Validação de visibilidade de registros em listas expandidas.

### Setup do Projeto
Siga os passos abaixo para configurar o ambiente em sua máquina local:

1 - Clone o repositório

2 - Vá para a pasta de Frontend usando cd Frontend

3 - Instale as dependências do Maven:

mvn clean install

4 - Executando os Testes

Para executar todos os testes contidos na pasta src/test/java utilize: mvn test

Para rodar apenas os testes da classe WebTablesTest por exemplo, utilize: mvn -Dtest=WebTablesTest test

#### Relatórios
Após a execução, os logs de sucesso ou falha são demonstrados diretamente no terminal. Os resultados detalhados também podem ser encontrados na pasta:
target/surefire-reports
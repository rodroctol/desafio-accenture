Aqui está a collection do Postman para automação de testes da API Book Store do DemoQA. A automação valida fluxos de autenticação, gerenciamento de usuários e reserva de livros.

# Endpoints Testados
1 - Contas
POST / User: Criação de novo usuário.

POST / GenerateToken: Geração de token de autenticação.

POST / Authorized: Validação de autorização do usuário.

GET / User / {UUID}: Consulta de dados do perfil e livros alugados.

DELETE / User / {UUID}: Remoção de conta.

2 - Livraria
GET / Books: Listagem de todos os livros disponíveis.

POST / Books: Aluguel de livros para o usuário alvo.

DELETE / Book: Remoção de um livro específico da coleção do usuário.

DELETE / Books: Limpeza total da coleção do usuário.

## Validações Realizadas (Scripts de Teste)
Em execução de scripts é validado:

Status Code: Validação se o retorno é 200 OK, 201 Created ou 204 No Content.

Schema JSON: Garantia de que a estrutura do corpo da resposta está correta.

Controle de Variáveis: Extração automática do userID e token para as próximas requisições.

Regras de Negócio:

Verificação se o livro alugado aparece no perfil do usuário.

Validação de erro ao tentar alugar o mesmo livro duas vezes.

Confirmação de que o usuário foi removido após o DELETE.

### Configuração da Collection no Postman:
1 - Variáveis de Ambiente
Para rodar a collection, você precisa criar um Environment no Postman com as seguintes chaves:

baseUrl: https://demoqa.com

userName: Seu nome de usuário.

password: Sua senha (deve conter caracteres especiais, maiúsculas e números).

2 - Como Rodar:
Importe o arquivo .json da collection.

Selecione o Environment configurado.

Selecione a collection e clique em Run.
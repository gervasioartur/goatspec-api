## Proposta de Resolução

Atendendo ao que foi pedido no exercício, apresento a seguinte resolução:

## Regras de Negócio
* Para fazer uma solicitação de especialização, você precisa estar logado como professor ou técnico.
* Para aprovar ou reprovar uma solicitação de especialização, você precisa estar logado como usuário administrador. O sistema já dispõe de um usuário admin padrão com as credenciais:
    * **CPF:** `admin`
    * **Senha:** `admin`
* Você somente pode remover as requisições de especialização criadas por você e se estas ainda estiverem no status pendente.

## Como Rodar o Sistema Localmente

Para rodar o sistema localmente, é necessário que você tenha o Docker instalado em sua máquina. Navegue até a raiz do projeto e execute o comando:


```bash
docker compose up
```
ou 

```bash
docker compose up -d
```

**Nota:** Aconselha-se a usar o comando `docker compose up -d`, pois, às vezes, o banco de dados leva mais tempo para estar pronto do que a aplicação. Caso a aplicação dê um crash, você pode rodar o comando novamente e testar a aplicação.

Após rodar o sistema localmente, acesse o Swagger para realizar os testes:

[http://localhost:8080/swagger-ui/index.html#//](http://localhost:8080/swagger-ui/index.html#/)

A  API também está hospedada no link do GPC: [https://goatspec-api-m24ry3ujja-rj.a.run.app/swagger-ui/index.html#/](https://goatspec-api-m24ry3ujja-rj.a.run.app/swagger-ui/index.html#/)

Repositório GitLab: [https://gitlab.com/dombo99/goatspec-api/](https://gitlab.com/dombo99/goatspec-api/)

Repositório Github: [https://github.com/gervasioartur/goatspec-api](https://github.com/gervasioartur/goatspec-api)

Métricas : [https://sonarcloud.io/summary/new_code?id=dombo99_goatspec-api](https://sonarcloud.io/summary/new_code?id=dombo99_goatspec-api)


## Tecnologias Utilizadas

- **Java 21**
- **Maven**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **JSON Web Token (JWT)**
- **Swagger**
- **Java Mail Sender**
- **Lombok**
- **JUnit**
- **PostgreSQL**
- **H2** (somente nos testes)
- **JaCoCo** (para cobertura de testes)
- **Docker**
- **GitLab CI** (para entrega contínua no GPC Cloud Runner)

## Boas Práticas de Desenvolvimento

- **Arquitetura Limpa**
- **Clean Code**
- **Design Patterns**
- **Small Commits**

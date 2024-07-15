## Proposta de Resolu��o

Atendendo ao que foi pedido no exerc�cio, apresento a seguinte resolu��o:

## Regras de Neg�cio
* Para fazer uma solicita��o de especializa��o, voc� precisa estar logado como professor ou t�cnico.
* Para aprovar ou reprovar uma solicita��o de especializa��o, voc� precisa estar logado como usu�rio administrador. O sistema j� disp�e de um usu�rio admin padr�o com as credenciais:
    * **CPF:** `admin`
    * **Senha:** `admin`
* Voc� somente pode remover as requisi��es de especializa��o criadas por voc� e se estas ainda estiverem no status pendente.

## Como Rodar o Sistema Localmente

Para rodar o sistema localmente, � necess�rio que voc� tenha o Docker instalado em sua m�quina. Navegue at� a raiz do projeto e execute o comando:


```bash
docker compose up
```
ou 

```bash
docker compose up -d
```

**Nota:** Aconselha-se a usar o comando `docker compose up -d`, pois, �s vezes, o banco de dados leva mais tempo para estar pronto do que a aplica��o. Caso a aplica��o d� um crash, voc� pode rodar o comando novamente e testar a aplica��o.

Ap�s rodar o sistema localmente, acesse o Swagger para realizar os testes:

[http://localhost:8080/swagger-ui/index.html#//](http://localhost:8080/swagger-ui/index.html#/)

A  API tamb�m est� hospedada no link do GPC: [https://goatspec-api-m24ry3ujja-rj.a.run.app/swagger-ui/index.html#/](https://goatspec-api-m24ry3ujja-rj.a.run.app/swagger-ui/index.html#/)

Reposit�rio GitLab: [https://gitlab.com/dombo99/goatspec-api/](https://gitlab.com/dombo99/goatspec-api/)

Reposit�rio Github: [https://github.com/gervasioartur/goatspec-api](https://github.com/gervasioartur/goatspec-api)

M�tricas : [https://sonarcloud.io/summary/new_code?id=dombo99_goatspec-api](https://sonarcloud.io/summary/new_code?id=dombo99_goatspec-api)


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
- **GitLab CI** (para entrega cont�nua no GPC Cloud Runner)

## Boas Pr�ticas de Desenvolvimento

- **Arquitetura Limpa**
- **Clean Code**
- **Design Patterns**
- **Small Commits**

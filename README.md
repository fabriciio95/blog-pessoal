# Blog Pessoal
[![NPM](https://img.shields.io/github/license/fabriciio95/blog-pessoal)](https://github.com/fabriciio95/blog-pessoal/blob/main/LICENSE) 

# Sobre o projeto

Blog Pessoal é uma API REST desenvolvida durante o bootcamp realizado pela [Generation Brasil](https://brazil.generation.org) para fins de aprendizado, onde usuários podem se cadastrar e fazer postagens temáticas.

## Modelo Lógico
![Logico](https://github.com/fabriciio95/arquivos-read-me/blob/master/arquivos-rep-blogpessoal/modelo-logico.jpg)

# Tecnologias utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Maven
- Jakarta Bean Validation
- JUnit

# Banco de Dados
- PostgreSQL

# Arquitetura
- REST

# Documentação Swagger
```bash
# usuario: admin
# senha: admin
https://appblogpessoal.herokuapp.com/swagger-ui/
```


# Como executar o projeto localmente
Pré-requisitos: Java 11, PostgreSQL

```bash

# clonar repositório
git clone https://github.com/fabriciio95/blog-pessoal.git

# Alterar no arquivo application.properties as propriedades de usuário e senha passando a senha de seu banco de dados local

# Entre na pasta raiz do projeto:
cd blog-pessoal/target

# E para rodar o projeto, você pode executar:
java -jar blog-pessoal-0.0.1-SNAPSHOT.jar
```

## Acessando a documentação no Swagger
Para acessar a documentação no Swagger, no seu navegador com a aplicação executando entre com a url:
```bash
# usuario: admin
# senha: admin
http://localhost:8080/swagger-ui/
```

# Autor

Fabricio Siqueira Macedo

https://linkedin.com/in/fabricio-siqueira-macedo

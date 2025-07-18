# LiterAlura - Seu Catálogo de Livros!

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Gutendex API](https://img.shields.io/badge/Gutendex%20API-lightgray?style=for-the-badge)

## Descrição do Projeto

O **LiterAlura** é um desafio proposto pela Alura, onde construímos nosso próprio catálogo de livros. O objetivo principal deste projeto é aplicar e praticar conceitos essenciais do desenvolvimento backend, como:
* Consumo de APIs externas (neste caso, a API gratuita [Gutendex](https://gutendex.com/)).
* Persistência de dados em um banco de dados relacional (PostgreSQL).
* Utilização do framework Spring Boot para construção de aplicações Java robustas.
* Modelagem de dados e manipulação através do Spring Data JPA.

Este catálogo permite ao usuário buscar livros, listar dados registrados, e consultar informações sobre autores, oferecendo uma experiência interativa via console.

## Tecnologias Utilizadas

* **Linguagem:** Java (versão 24, ou a versão configurada em seu `pom.xml` e no IntelliJ)
* **Framework:** Spring Framework (com Spring Boot)
* **Banco de Dados:** PostgreSQL
* **Persistência:** Spring Data JPA (com Hibernate)
* **Gerenciador de Dependências:** Apache Maven
* **API Consumida:** Gutendex API (API pública e gratuita de livros)
* **IDE:** IntelliJ IDEA
* **Processamento JSON:** Jackson (incluído via `spring-boot-starter-web`)
* **HTTP Client:** `java.net.http.HttpClient` (Java Standard Library)

## Como Rodar o Projeto

Para executar o LiterAlura em sua máquina local, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter instalado:
* **Java Development Kit (JDK)**: Versão 17 ou superior (preferencialmente a mesma configurada no `pom.xml`, que é 24 neste projeto).
* **Apache Maven**: Versão 3.x ou superior.
* **PostgreSQL**: Servidor de banco de dados instalado e rodando.
* **pgAdmin** (Opcional, mas recomendado para visualizar o banco de dados).
* **IntelliJ IDEA** (Opcional, mas recomendado como IDE).

### Configuração do Banco de Dados

1.  **Crie um banco de dados no PostgreSQL:**
    * Abra o pgAdmin.
    * Conecte-se ao seu servidor PostgreSQL.
    * Crie um novo banco de dados com o nome `literalura_db`.
    * Anote a senha do seu usuário `postgres`.

2.  **Configure as credenciais no projeto:**
    * Abra o arquivo `src/main/resources/application.properties`.
    * Atualize a linha `spring.datasource.password=sua_senha_do_postgres` com a senha real do seu usuário PostgreSQL.

### Executando a Aplicação

1.  **Clone ou baixe o projeto:**
    * Certifique-se de que o projeto esteja na pasta `A:\Amaro Netto\AMARO BACKUP\Projetos\LiterAlura_JavaOneAlura`.

2.  **Abra o projeto no IntelliJ IDEA:**
    * No IntelliJ, vá em `File > Open...` e selecione a pasta raiz do projeto (`LiterAlura_JavaOneAlura`).
    * Aguarde o IntelliJ carregar as dependências Maven (se houver um pop-up "Load Maven changes", clique nele, ou vá na aba "Maven" na lateral direita e clique em "Reload All Maven Projects").

3.  **Execute a classe principal:**
    * Navegue até `src/main/java/com/literalura/LiterAluraApplication.java`.
    * Clique no ícone de "play" (triângulo verde) ao lado do método `main` e selecione "Run 'LiterAluraApplication.main()'".

4.  **Interaja via console:**
    * A aplicação será iniciada e um menu interativo aparecerá no console do IntelliJ, permitindo que você interaja com as funcionalidades do catálogo.

## Funcionalidades Implementadas

O LiterAlura oferece as seguintes funcionalidades através de um menu interativo no console:

1.  **Buscar livro pelo título:** Realiza uma consulta na API Gutendex e registra o primeiro livro encontrado (com seu autor) no banco de dados local, se ainda não estiver lá.
2.  **Listar livros registrados:** Exibe todos os livros que foram salvos no banco de dados.
3.  **Listar autores registrados:** Exibe todos os autores que foram salvos no banco de dados, incluindo os títulos dos livros que cada um escreveu.
4.  **Listar autores vivos em determinado ano:** Permite buscar e exibir autores que estavam vivos em um ano específico, com base nos seus anos de nascimento e falecimento.
5.  **Listar livros em determinado idioma:** Filtra e exibe os livros registrados no banco de dados por um idioma específico (es, en, fr, pt).
6.  **Gerar estatísticas dos livros:** Exibe o total de livros, a média de downloads e o livro com o maior número de downloads.
7.  **Gerar Top 10 livros mais baixados:** Lista os 10 livros com o maior número de downloads do seu catálogo.
8.  **Buscar autor pelo nome:** Permite buscar e exibir os detalhes de um autor específico, incluindo seus livros, pelo nome.
9.  **Listar autores em ordem alfabética:** Exibe todos os autores registrados no banco de dados em ordem alfabética pelo nome.

## Próximas Melhorias (Sugestões)

* **Criação de Interface Gráfica (GUI):** Evoluir a aplicação de console para uma interface gráfica de usuário, oferecendo uma experiência mais visual e interativa (utilizando tecnologias como JavaFX ou Swing).
* **Listar Livros por Categoria/Gênero:** Adicionar uma opção para filtrar livros por categorias ou assuntos (utilizando os campos 'subjects' da API Gutendex).
* **Remover Livro do Banco de Dados:** Implementar a funcionalidade para deletar um livro registrado.
* **Listar Livros de um Autor Específico:** Criar uma opção para listar todos os livros de um autor escolhido.
* **Gerar Relatórios Avançados:** Explorar a geração de relatórios mais complexos ou talvez uma representação mais visual (mesmo que textual) dos dados.

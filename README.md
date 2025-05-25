# 📋 Sistema de Gerenciamento de Tarefas — JSF + PrimeFaces + JPA

Este projeto consiste em um sistema web simples de gerenciamento de tarefas, desenvolvido com **JavaServer Faces (JSF)**, **PrimeFaces**, **Hibernate** e **JPA**, utilizando **PostgreSQL** como banco de dados.

A aplicação permite ao usuário:
- Cadastrar novas tarefas;
- Editar tarefas existentes;
- Excluir tarefas;
- Concluir tarefas;
- Filtrar tarefas com base em determinados campos.

### ✅ Funcionalidades Implementadas

a) Cadastro de tarefas com os seguintes campos:
* Título
* Descrição
* Responsável (enum)
* Prioridade (enum)
* Deadline
* Status (enum)

b) Validação de campos obrigatórios usando Bean Validation (`@NotBlank`).

c) Edição de tarefas com base no ID via parâmetro na URL.

d) Exclusão de tarefas diretamente pela listagem.

e) Conclusão de tarefas: a tarefa muda seu status para *"Concluída"*.

f) Listagem de todas as tarefas em uma tabela com paginação e ordenação por data (`p:dataTable`).

g) Filtros de busca por:
* Título/Descrição
* Responsável
* Status

h) Utilização de enums (`Status`, `Priority`, `Responsible`) nas entidades e nas interfaces.

i) Redirecionamento via **FacesContext** ao editar ou voltar à listagem.

j) Carregamento de dados iniciais automaticamente via script SQL.

### ⚙️ Instruções para Execução em Ambiente Local

1. Requisitos
    * Java 8 ou superior
    * Maven
	*	PostgreSQL
	*	Servidor de aplicação compatível com JSF (ex: Tomcat 9+)
2. Configurar o banco de dados
	Crie um banco no PostgreSQL:

		```sql
		CREATE DATABASE tasks_management_jsf;
		```
	Usuário e senha padrão usados no projeto:
	* Usuário: `postgres`
	* Senha: `postgres`

	**Caso deseje alterar, modifique o arquivo**`persistence.xml`.

3. Estrutura de arquivos relevantes

  * `persistence.xml` – Configuração da JPA e Hibernate, e integração com o banco PostgreSQL.
  * `dados-iniciais.sql` – Script SQL com tarefas de exemplo.
  * `Task.java` – Entidade JPA que representa a tarefa.
  * `Status.java`, `Priority.java`, `Responsible.java` – Enums utilizados nos campos da tarefa.
  * `JPAUtil.java` – Classe utilitária para o `EntityManager`.
  * `TaskRepository.java` – Classe responsável pelas operações de persistência (CRUD + filtros).
  * `TaskBean.java` – Bean de controle da lógica de cadastro, edição e listagem.

4. Rodar o projeto
    Importe o projeto em sua IDE Java (Eclipse, IntelliJ, VS Code com suporte para Java).

    Certifique-se de que o PostgreSQL está rodando e que o banco **tasks_management_jsf** foi criado.
  
    Execute o servidor de aplicação (Tomcat, Payara, etc.).

	  Acesse a aplicação em:
  	```
  	http://localhost:8080/nome-da-sua-aplicacao/listagem_tarefas.xhtml
  	```
6. Sobre o script de dados
O arquivo `dados-iniciais.sql` está configurado para ser executado automaticamente com a opção `hibernate.hbm2ddl.auto = create-drop`. Ele insere registros exemplo ao iniciar a aplicação.

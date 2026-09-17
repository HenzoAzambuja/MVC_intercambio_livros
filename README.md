# 📚 BookSwap - Sistema de Intercâmbio de Livros

Trata-se de uma aplicação web completa desenvolvida em **Java** utilizando a arquitetura **MVC (Model-View-Controller)**, com camadas adicionais de **DAO (Data Access Object)** e **Service**, persistência em banco de dados **MySQL** e execução conteinerizada via **Docker**.

---

## 👥 Integrantes da Dupla

- **Henzo Severino de Azambuja Nunes**
- **João Victor de Carvalho Cândido**

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java
- **Plataforma Web:** Jakarta EE 10 (Jakarta Servlet 6.0 & Jakarta JSTL 3.0)
- **Servidor de Aplicação:** Apache Tomcat 10.1
- **Banco de Dados:** MySQL 8.4
- **Gerenciador de Dependências e Build:** Apache Maven
- **Conteinerização:** Docker & Docker Compose
- **Front-end:** JSP (JavaServer Pages), JSTL, HTML5, CSS3 responsivo e JavaScript

---

## 🏛️ Arquitetura e Padrões de Projeto

A aplicação adota separação rigorosa de responsabilidades:

```
src/main/
├── java/br/com/intercambiolivros/
│   ├── config/          # Configuração e Singleton de Conexão JDBC (MysqlSingleton)
│   ├── controller/      # Servlets / Controllers (BaseServlet, LivroServlet, etc.)
│   ├── dao/             # Camada de Acesso a Dados com PreparedStatement (LivroDAO, etc.)
│   ├── filter/          # Filtro de Autenticação e Controle de Acesso (AuthFilter)
│   ├── model/           # Modelos de Domínio / JavaBeans (Livro, Usuario, Troca, Perfil)
│   └── service/         # Camada de Negócio e Validações (LivroService, etc.)
└── webapp/
    ├── css/             # Folha de estilos personalizada e responsiva (estilo.css)
    ├── js/              # Scripts de suporte e interações do usuário (script.js)
    ├── WEB-INF/
    │   ├── jsp/         # Views protegidas (home, livros, trocas, usuarios, pessoas)
    │   └── web.xml      # Descritor de implantação da aplicação
    └── index.jsp        # Ponto de entrada (redireciona para /home)
```

### Padrões Implementados:
- **MVC (Model-View-Controller):** 
  - **Model:** Classes em `br.com.intercambiolivros.model`.
  - **View:** JSPs localizados dentro de `WEB-INF/jsp/` (garantindo que o cliente nunca acesse as páginas diretamente sem passar por um Controller).
  - **Controller:** Servlets mapeados com `@WebServlet` herdando de `BaseServlet` para tratamento padronizado de parâmetros, redirecionamentos e encaminhamentos.
- **DAO (Data Access Object):** Centraliza comandos SQL em classes filhas de `MysqlDAO`, utilizando sempre `PreparedStatement` para prevenir ataques de *SQL Injection*.
- **Service:** Isola regras de negócio, validações de formulário, cálculos e integridade (ex: validação de e-mail por regex, tamanho de senha, unicidade de dados e regras para troca de livros).
- **Singleton:** `MysqlSingleton` gerencia uma conexão reutilizável com o banco de dados.
- **Front Controller / Filter:** `AuthFilter` intercepta requisições privadas, garantindo sessão autenticada e autorização por perfil (ex: tela de usuários restrita a Administradores).

---

## 🚀 Funcionalidades Principais

### 1. Autenticação e Autorização
- Login e Logout com controle de sessão (`HttpSession`).
- Cadastro público de novos usuários (perfil "Cliente").
- Proteção de rotas via `AuthFilter` e controle de permissões por perfil (Administrador vs. Cliente).

### 2. CRUD Completo de Livros
- **Create:** Cadastro de novos livros pelo usuário proprietário.
- **Read:** 
  - Catálogo de livros disponíveis de outros usuários para troca (`/livros`).
  - Meus Livros (`/livros?acao=meus`): listagem gerencial dos próprios livros.
- **Update:** Edição de dados do livro e alternância de status de disponibilidade para troca.
- **Delete:** Exclusão do livro com validação de segurança (impede exclusão se o livro possui histórico em trocas para preservar integridade referencial).

### 3. CRUD Completo de Usuários (Administrador)
- Gestão completa de contas de usuário (criação, edição de dados e perfis, listagem e inativação lógica/soft delete).

### 4. Sistema de Trocas (Regras de Negócio Avançadas)
- Proposta de troca entre um livro do usuário solicitante e um livro disponível de outro usuário.
- Fluxo de status da troca: `PENDENTE` ➔ `ACEITA` ou `RECUSADA`.
- Conclusão da troca: transfere a titularidade dos livros entre os usuários no banco de dados e atualiza o histórico em "Trocas Concluídas".

### 5. Catálogo de Pessoas
- Visualização de outros usuários da comunidade e consulta direta dos livros que cada pessoa possui disponíveis para troca.

---

## 🔑 Credenciais para Teste

O script de inicialização do banco (`init.sql`) já popula usuários de teste:

| Tipo de Usuário | Login | Senha | Acesso Especial |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin` | `123456` | Acesso ao CRUD de Usuários e todas as telas |
| **Cliente 1** | `dani` | `123456` | Dono de livros de teste e trocas |
| **Cliente 2** | `alexandre` | `123456` | Dono de livros de teste |
| **Cliente 3** | `dilma` | `123456` | Dono de livros de teste |

---

## ⚙️ Como Executar o Projeto

### Via Docker Compose 

Certifique-se de ter o **Docker** e o **Docker Compose** instalados na sua máquina.

1. No terminal, na raiz do projeto, execute:
   ```bash
   docker compose up -d
   ```
   *(ou `docker-compose up -d`)*

2. O Docker iniciará:
   - Container do **MySQL 8.4** executando automaticamente o script `init.sql`.
   - Container do **Apache Tomcat 10.1** implantando a aplicação contida na pasta `deploy`.

3. Abra o navegador e acesse:
   ```text
   http://localhost:8080/intercambiolivros
   ```

4. Para parar os containers:
   ```bash
   docker compose down
   ```

---




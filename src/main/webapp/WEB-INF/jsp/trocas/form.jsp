<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Nova troca - Intercambio de Livros
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilo.css">

</head>

<body>

<header class="topbar">

    <div class="container">

        <strong>
            Intercambio de Livros
        </strong>

        <nav>

            <a href="${pageContext.request.contextPath}/home">
                Home
            </a>

            <a href="${pageContext.request.contextPath}/livros">
                Livros
            </a>

            <a href="${pageContext.request.contextPath}/livros?acao=meus">
                Meus livros
            </a>

            <a href="${pageContext.request.contextPath}/pessoas">
                Pessoas
            </a>

            <a href="${pageContext.request.contextPath}/trocas">
                Trocas
            </a>

            <a href="${pageContext.request.contextPath}/logout">
                Sair
            </a>

        </nav>

    </div>

</header>

<main class="container">

    <div class="page-header">

        <h1>
            Nova troca
        </h1>

    </div>

    <div class="card">

        <c:if test="${not empty erro}">

            <div class="alert alert-erro">
                ${erro}
            </div>

        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/trocas">

            <div class="form-group">

                <label for="livroOferecidoId">
                    Meu livro
                </label>

                <select id="livroOferecidoId"
                        name="livroOferecidoId"
                        required>

                    <option value="">
                        Selecione seu livro
                    </option>

                    <c:forEach
                            var="livro"
                            items="${meusLivros}">

                        <option value="${livro.id}">

                            ${livro.titulo}
                            - ${livro.autor}

                        </option>

                    </c:forEach>

                </select>

            </div>

            <div class="form-group">

                <label for="livroRecebidoId">
                    Livro que desejo receber
                </label>

                <select id="livroRecebidoId"
                        name="livroRecebidoId"
                        required>

                    <option value="">
                        Selecione um livro
                    </option>

                    <c:forEach
                            var="livro"
                            items="${livrosDisponiveis}">

                        <option value="${livro.id}">

                            ${livro.titulo}
                            - ${livro.autor}
                            (${livro.usuario.nome})

                        </option>

                    </c:forEach>

                </select>

            </div>

            <div class="form-group">

                <label for="mensagem">
                    Mensagem / local da troca
                </label>

                <textarea id="mensagem"
                          name="mensagem"
                          rows="5"
                          required
                          placeholder="Ex.: Podemos realizar a troca na biblioteca da faculdade, na sexta-feira às 14h."></textarea>

            </div>

            <div class="actions">

                <button type="submit"
                        class="btn">

                    Solicitar troca

                </button>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/trocas">

                    Cancelar

                </a>

            </div>

        </form>

    </div>

</main>

</body>

</html>
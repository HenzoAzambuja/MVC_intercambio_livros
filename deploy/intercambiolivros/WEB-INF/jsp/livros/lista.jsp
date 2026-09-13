<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Livros disponíveis - Intercambio de Livros
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilo.css">

</head>

<body>

<header class="topbar">

    <div class="container">

        <strong>Intercambio de Livros</strong>

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

            <a href="${pageContext.request.contextPath}/logout">
                Sair
            </a>

        </nav>

    </div>

</header>

<main class="container">

    <div class="page-header">

        <h1>
            Livros disponiveis
        </h1>

        <a class="btn"
           href="${pageContext.request.contextPath}/livros?acao=meus">

            Meus livros

        </a>

    </div>


    <c:if test="${not empty erro}">

        <div class="alert alert-erro">
            ${erro}
        </div>

    </c:if>


    <c:choose>

        <c:when test="${empty livros}">

            <div class="card">

                <p class="empty">
                    Nenhum livro disponivel no momento.
                </p>

            </div>

        </c:when>


        <c:otherwise>

            <div class="grid-cards">

                <c:forEach var="livro" items="${livros}">

                    <div class="menu-card">

                        <strong>
                            ${livro.titulo}
                        </strong>

                        <span>
                            Autor: ${livro.autor}
                        </span>

                        <br>

                        <span>
                            Proprietario: ${livro.usuario.nome}
                        </span>

                    </div>

                </c:forEach>

            </div>

        </c:otherwise>

    </c:choose>

</main>

</body>

</html>
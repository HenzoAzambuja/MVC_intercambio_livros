<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Meus livros - BookSwap
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilo.css">

</head>

<body>

<header class="topbar">

    <div class="container">

        <strong>
            BookSwap
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

            <c:if test="${sessionScope.usuarioLogado.perfilId == 1}">
                <a href="${pageContext.request.contextPath}/usuarios">
                    Usuarios
                </a>
            </c:if>

            <a href="${pageContext.request.contextPath}/logout">
                Sair
            </a>

        </nav>

    </div>

</header>

<main class="container">

    <div class="page-header">

        <h1>
            Meus livros
        </h1>

        <a class="btn"
           href="${pageContext.request.contextPath}/livros?acao=novo">

            + Adicionar livro

        </a>

    </div>

    <c:if test="${not empty erro}">

        <div class="alert alert-erro">
            <c:out value="${erro}" />
        </div>

    </c:if>

    <c:choose>

        <c:when test="${empty livros}">

            <div class="card">

                <p class="empty">
                    Voce ainda nao possui livros cadastrados.
                </p>

                <a class="btn"
                   href="${pageContext.request.contextPath}/livros?acao=novo">

                    Adicionar primeiro livro

                </a>

            </div>

        </c:when>

        <c:otherwise>

            <div class="table-wrap">

                <table>

                    <thead>

                    <tr>

                        <th>ID</th>
                        <th>Titulo</th>
                        <th>Autor</th>
                        <th>Disponibilidade</th>
                        <th>Acoes</th>

                    </tr>

                    </thead>

                    <tbody>

                    <c:forEach var="livro" items="${livros}">

                        <tr>

                            <td>
                                ${livro.id}
                            </td>

                            <td>
                                <c:out value="${livro.titulo}" />
                            </td>

                            <td>
                                <c:out value="${livro.autor}" />
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${livro.disponivel}">
                                        Disponivel para troca
                                    </c:when>
                                    <c:otherwise>
                                        Indisponivel
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td class="links">

                                <a href="${pageContext.request.contextPath}/livros?acao=disponibilidade&id=${livro.id}">
                                    <c:choose>
                                        <c:when test="${livro.disponivel}">Tornar indisponivel</c:when>
                                        <c:otherwise>Disponibilizar para troca</c:otherwise>
                                    </c:choose>
                                </a>

                                <a href="${pageContext.request.contextPath}/livros?acao=editar&id=${livro.id}">
                                    Editar
                                </a>

                                <a href="${pageContext.request.contextPath}/livros?acao=excluir&id=${livro.id}"
                                   onclick="return confirm('Excluir este livro?');">

                                    Excluir

                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </div>

        </c:otherwise>

    </c:choose>

</main>

</body>

</html>
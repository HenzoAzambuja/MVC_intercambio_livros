<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Trocas concluidas - Intercambio de Livros
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

        <div>

            <h1>
                Trocas concluidas
            </h1>

            <p>
                Historico das trocas realizadas.
            </p>

        </div>

        <a class="btn"
           href="${pageContext.request.contextPath}/trocas">

            Voltar para trocas

        </a>

    </div>

    <c:choose>

        <c:when test="${empty trocasConcluidas}">

            <div class="card">

                <p class="empty">
                    Nenhuma troca concluida.
                </p>

            </div>

        </c:when>

        <c:otherwise>

            <div class="grid-cards">

                <c:forEach
                        var="troca"
                        items="${trocasConcluidas}">

                    <div class="menu-card">

                        <h3>
                            Troca #${troca.id}
                        </h3>

                        <p>

                            <strong>
                                Livro oferecido:
                            </strong>

                            ${troca.livroOferecido.titulo}

                        </p>

                        <p>

                            <strong>
                                Livro recebido:
                            </strong>

                            ${troca.livroRecebido.titulo}

                        </p>

                        <p>

                            <strong>
                                Mensagem:
                            </strong>

                            ${troca.mensagem}

                        </p>

                        <p>

                            <strong>
                                Status:
                            </strong>

                            Concluida

                        </p>

                    </div>

                </c:forEach>

            </div>

        </c:otherwise>

    </c:choose>

</main>

</body>

</html>
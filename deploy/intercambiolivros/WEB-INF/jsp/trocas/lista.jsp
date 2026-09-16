<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Minhas trocas - Intercambio de Livros
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
                Minhas trocas
            </h1>

            <p>
                Acompanhe suas solicitações e trocas.
            </p>

        </div>

        <a class="btn"
           href="${pageContext.request.contextPath}/trocas?acao=nova">

            + Nova troca

        </a>

        <a class="btn btn-secondary"
           href="${pageContext.request.contextPath}/trocas?acao=concluidas">

            Trocas concluidas

        </a>

    </div>

    <c:if test="${not empty sessionScope.erroTroca}">

        <div class="alert alert-erro">
            ${sessionScope.erroTroca}
        </div>

        <c:remove var="erroTroca"
                  scope="session"/>

    </c:if>

    <c:choose>

        <c:when test="${empty trocas}">

            <div class="card">

                <p class="empty">
                    Voce ainda nao possui trocas.
                </p>

            </div>

        </c:when>

        <c:otherwise>

            <div class="grid-cards">

                <c:forEach
                        var="troca"
                        items="${trocas}">

                    <div class="menu-card">

                        <h3>
                            Troca #${troca.id}
                        </h3>

                        <p>

                            <strong>
                                Oferecido:
                            </strong>

                            ${troca.livroOferecido.titulo}

                        </p>

                        <p>

                            <strong>
                                Recebido:
                            </strong>

                            ${troca.livroRecebido.titulo}

                        </p>

                        <p>

                            <strong>
                                Usuario:
                            </strong>

                            ${troca.livroRecebido.usuario.nome}

                        </p>

                        <p>
                            <strong>Quem oferece:</strong>
                            ${troca.livroOferecido.usuario.nome}
                        </p>

                        <p>
                            <strong>Quem recebe:</strong>
                            ${troca.livroRecebido.usuario.nome}
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

                            ${troca.status}

                        </p>

                        <c:if test="${troca.status == 'PENDENTE'}">

                            <div class="actions">

                                <a href="${pageContext.request.contextPath}/trocas?acao=aceitar&id=${troca.id}"
                                   class="btn">

                                    Aceitar

                                </a>

                                <a href="${pageContext.request.contextPath}/trocas?acao=recusar&id=${troca.id}"
                                   class="btn btn-secondary">

                                    Recusar

                                </a>

                            </div>

                        </c:if>

                        <c:if test="${troca.status == 'ACEITA'}">

                            <div class="actions">

                                <a href="${pageContext.request.contextPath}/trocas?acao=concluir&id=${troca.id}"
                                   class="btn">

                                    Concluir troca

                                </a>

                            </div>

                        </c:if>

                    </div>

                </c:forEach>

            </div>

        </c:otherwise>

    </c:choose>

</main>

</body>

</html>
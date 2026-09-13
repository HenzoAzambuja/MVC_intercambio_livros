<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Pessoas - Intercambio de Livros</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<header class="topbar">
    <div class="container">
        <strong>Intercambio de Livros</strong>

        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/livros">Livros</a>
            <a href="${pageContext.request.contextPath}/livros?acao=meus">Meus livros</a>
            <a href="${pageContext.request.contextPath}/pessoas">Pessoas</a>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </div>
</header>

<main class="container">

    <c:choose>
        <c:when test="${not empty usuarioPerfil}">
            <div class="page-header">
                <div>
                    <h1>${usuarioPerfil.nome}</h1>
                    <p>Veja os livros disponíveis para intercâmbio.</p>
                </div>

                <a href="${pageContext.request.contextPath}/pessoas">
                    Voltar para pessoas
                </a>
            </div>

            <section class="home-section">
                <h2>Livros de ${usuarioPerfil.nome}</h2>

                <c:choose>
                    <c:when test="${empty livrosPessoa}">
                        <div class="card">
                            <p class="empty">Esta pessoa ainda não possui livros cadastrados.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="grid-cards">
                            <c:forEach var="livro" items="${livrosPessoa}">
                                <div class="menu-card">
                                    <h3>${livro.titulo}</h3>
                                    <p>${livro.autor}</p>
                                    <span>Disponível para intercâmbio</span>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </c:when>

        <c:otherwise>
            <div class="page-header">
                <div>
                    <h1>Pessoas</h1>
                    <p>Encontre pessoas e veja seus livros disponíveis.</p>
                </div>
            </div>

            <c:choose>
                <c:when test="${empty usuarios}">
                    <div class="card">
                        <p class="empty">Nenhuma outra pessoa cadastrada.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid-cards">
                        <c:forEach var="usuario" items="${usuarios}">
                            <div class="menu-card">
                                <h3>${usuario.nome}</h3>
                                <p>Veja os livros disponíveis desta pessoa.</p>
                                <a href="${pageContext.request.contextPath}/pessoas?id=${usuario.id}">
                                    Ver perfil →
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</main>

</body>
</html>

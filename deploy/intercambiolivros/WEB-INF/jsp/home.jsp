<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!DOCTYPE html>
        <html lang="pt-BR">

        <head>

            <meta charset="UTF-8">

            <meta name="viewport" content="width=device-width, initial-scale=1">

            <title>
                Home - BookSwap
            </title>

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">

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

                        <a href="${pageContext.request.contextPath}/trocas">
                            Trocas
                        </a>

                        <a href="${pageContext.request.contextPath}/pessoas">
                            Pessoas
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
                            Olá, <c:out value="${sessionScope.usuarioLogado.nome}" />!
                        </h1>

                        <p>
                            Encontre livros e pessoas para fazer intercâmbios.
                        </p>

                    </div>

                </div>


                <!-- LIVROS DISPONIVEIS -->

                <section class="home-section">

                    <div class="section-header">

                        <h2>
                            Livros disponíveis
                        </h2>

                        <a href="${pageContext.request.contextPath}/livros">
                            Ver todos →
                        </a>

                    </div>


                    <c:choose>

                        <c:when test="${empty livrosDisponiveis}">

                            <div class="card">

                                <p class="empty">
                                    Nenhum livro disponível no momento.
                                </p>

                            </div>

                        </c:when>

                        <c:otherwise>

                            <div class="grid-cards">

                                <c:forEach var="livro" items="${livrosDisponiveis}">

                                    <div class="menu-card">

                                        <h3>
                                            <c:out value="${livro.titulo}" />
                                        </h3>

                                        <p>
                                            <c:out value="${livro.autor}" />
                                        </p>

                                        <span>
                                            Dono: <c:out value="${livro.usuario.nome}" />
                                        </span>

                                    </div>

                                </c:forEach>

                            </div>

                        </c:otherwise>

                    </c:choose>

                </section>


                <!-- MEUS LIVROS -->

                <section class="home-section">

                    <div class="section-header">

                        <h2>
                            Meus livros
                        </h2>

                        <a href="${pageContext.request.contextPath}/livros?acao=meus">
                            Ver meus →
                        </a>

                    </div>


                    <c:choose>

                        <c:when test="${empty meusLivros}">

                            <div class="card">

                                <p class="empty">
                                    Você ainda não possui livros cadastrados.
                                </p>

                                <a class="btn" href="${pageContext.request.contextPath}/livros?acao=novo">

                                    Adicionar livro

                                </a>

                            </div>

                        </c:when>

                        <c:otherwise>

                            <div class="grid-cards">

                                <c:forEach var="livro" items="${meusLivros}">

                                    <div class="menu-card">

                                        <h3>
                                            <c:out value="${livro.titulo}" />
                                        </h3>

                                        <p>
                                            <c:out value="${livro.autor}" />
                                        </p>
                                    </div>

                                </c:forEach>

                            </div>

                        </c:otherwise>

                    </c:choose>

                </section>

                <!-- TROCAS -->

                <section class="home-section">

                    <div class="section-header">

                        <h2>
                            Trocas
                        </h2>

                        <a href="${pageContext.request.contextPath}/trocas">
                            Ver todas →
                        </a>

                    </div>

                    <c:choose>

                        <c:when test="${empty trocas}">

                            <div class="card">

                                <p class="empty">
                                    Voce ainda nao possui trocas.
                                </p>

                                <a class="btn"
                                href="${pageContext.request.contextPath}/trocas?acao=nova">

                                    Fazer uma troca

                                </a>

                            </div>

                        </c:when>

                        <c:otherwise>

                            <div class="grid-cards">

                                <c:forEach
                                        var="troca"
                                        items="${trocas}">

                                    <div class="menu-card">

                                        <h3>
                                            Troca #<c:out value="${troca.id}" />
                                        </h3>

                                        <p>

                                            <c:out value="${troca.livroOferecido.titulo}" />

                                            ↔

                                            <c:out value="${troca.livroRecebido.titulo}" />

                                        </p>

                                        <span>
                                            Status: <c:out value="${troca.status}" />
                                        </span>

                                    </div>

                                </c:forEach>

                            </div>

                        </c:otherwise>

                    </c:choose>

                </section>

                <!-- PESSOAS -->

                <section class="home-section">

                    <div class="section-header">

                        <h2>
                            Pessoas
                        </h2>

                        <a href="${pageContext.request.contextPath}/pessoas">
                            Ver todos →
                        </a>

                    </div>


                    <c:choose>

                        <c:when test="${empty usuarios}">

                            <div class="card">

                                <p class="empty">
                                    Nenhuma outra pessoa cadastrada.
                                </p>

                            </div>

                        </c:when>

                        <c:otherwise>

                            <div class="grid-cards">

                                <c:forEach var="usuario" items="${usuarios}">

                                    <div class="menu-card">

                                        <h3>
                                            <c:out value="${usuario.nome}" />
                                        </h3>

                                        <span>
                                            Veja os livros disponíveis desta pessoa.
                                        </span>

                                        <br>

                                        <a href="${pageContext.request.contextPath}/pessoas?id=${usuario.id}">

                                            Ver perfil →

                                        </a>

                                    </div>

                                </c:forEach>

                            </div>

                        </c:otherwise>

                    </c:choose>

                </section>

            </main>

        </body>

        </html>
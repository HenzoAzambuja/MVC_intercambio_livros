<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Home - BookSwap</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<header class="topbar">

    <div class="container">

        <strong>BookSwap</strong>

        <nav>

            <span>
                ${usuarioLogado.nome}
            </span>

            <a href="${pageContext.request.contextPath}/logout">
                Sair
            </a>

        </nav>

    </div>

</header>

<main class="container">

    <div class="page-header">
        <h1>Painel</h1>
    </div>

    <div class="grid-cards">

        <a class="menu-card"
           href="${pageContext.request.contextPath}/livros">

            <strong>Livros</strong>

            <span>
                Listar, cadastrar, editar e excluir livros.
            </span>

        </a>

        <a class="menu-card"
           href="${pageContext.request.contextPath}/trocas">

            <strong>Trocas</strong>

            <span>
                Gerenciar solicitações de troca de livros.
            </span>

        </a>

        <a class="menu-card"
           href="${pageContext.request.contextPath}/usuarios">

            <strong>Usuários</strong>

            <span>
                Listar e gerenciar usuários cadastrados.
            </span>

        </a>

    </div>

</main>

</body>
</html>
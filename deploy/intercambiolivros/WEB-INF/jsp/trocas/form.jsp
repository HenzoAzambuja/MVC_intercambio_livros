<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Solicitar troca - BookSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>
    <header class="topbar">
        <div class="container nav"><a class="brand" href="../home.jsp">Book<span>Swap</span></a>
            <nav class="menu"><a href="../home.jsp">Início</a><a href="../livros/lista.jsp">Livros</a><a
                    href="lista.jsp">Minhas Trocas</a><a href="../usuarios/lista.jsp">Perfil</a></nav>
        </div>
    </header>
    <main class="page">
        <div class="container">
            <div class="page-title">
                <h1>Solicitar troca</h1>
                <div class="muted">Escolha um livro seu para oferecer em troca.</div>
            </div>
            <div class="panel">
                <div class="notice">Você está solicitando <strong>O Hobbit</strong>, de J. R. R. Tolkien.</div>
                <div class="field"><label>Livro que você oferece</label><select class="select">
                        <option>Clean Code — Robert C. Martin</option>
                        <option>Dom Casmurro — Machado de Assis</option>
                        <option>O Pequeno Príncipe — Antoine de Saint-Exupéry</option>
                    </select></div>
                <div class="field"><label>Mensagem (opcional)</label><textarea class="textarea" rows="4"
                        placeholder="Escreva uma mensagem para o outro usuário..."></textarea></div>
                <div class="actions"><button class="btn btn-primary">Enviar solicitação</button><a
                        class="btn btn-outline" href="lista.jsp">Cancelar</a></div>
            </div>
        </div>
    </main>
</body>

</html>
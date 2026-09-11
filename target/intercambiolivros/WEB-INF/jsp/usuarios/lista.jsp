<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Perfil - BookSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>
    <header class="topbar">
        <div class="container nav"><a class="brand" href="../home.jsp">Book<span>Swap</span></a>
            <nav class="menu"><a href="../home.jsp">Início</a><a href="../livros/lista.jsp">Livros</a><a
                    href="../trocas/lista.jsp">Minhas Trocas</a><a href="lista.jsp">Perfil</a></nav>
        </div>
    </header>
    <main class="page">
        <div class="container">
            <div class="page-title">
                <h1>Meu perfil</h1>
                <div class="muted">Dados do usuário e resumo da atividade.</div>
            </div>
            <div class="panel">
                <h2 style="margin-top:0">Henzo Severino</h2>
                <p class="muted">henzo@email.com</p>
                <div class="stats" style="margin-top:18px">
                    <div class="stat"><strong>5</strong>livros cadastrados</div>
                    <div class="stat"><strong>3</strong>trocas realizadas</div>
                    <div class="stat"><strong>2</strong>trocas pendentes</div>
                </div>
            </div>
        </div>
    </main>
</body>

</html>
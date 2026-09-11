<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Minhas Trocas - BookSwap</title>
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
                <h1>Minhas trocas</h1>
                <div class="muted">Acompanhe as solicitações de intercâmbio.</div>
            </div>
            <div class="panel">
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Meu livro</th>
                                <th>Livro desejado</th>
                                <th>Usuário</th>
                                <th>Status</th>
                                <th>Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Clean Code</td>
                                <td>O Hobbit</td>
                                <td>Maria</td>
                                <td><span class="status pending">Pendente</span></td>
                                <td><button class="btn btn-primary">Cancelar</button></td>
                            </tr>
                            <tr>
                                <td>Dom Casmurro</td>
                                <td>O Pequeno Príncipe</td>
                                <td>João</td>
                                <td><span class="status">Aceita</span></td>
                                <td><button class="btn btn-outline">Detalhes</button></td>
                            </tr>
                            <tr>
                                <td>O Hobbit</td>
                                <td>Clean Code</td>
                                <td>Pedro</td>
                                <td><span class="status danger">Recusada</span></td>
                                <td><button class="btn btn-outline">Detalhes</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</body>

</html>
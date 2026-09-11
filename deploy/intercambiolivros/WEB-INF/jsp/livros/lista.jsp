<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Livros - BookSwap</title>
    <link rel="stylesheet" href="../../../css/estilo.css">
</head>

<body>
    <header class="topbar">
        <div class="container nav"><a class="brand" href="../home.jsp">Book<span>Swap</span></a>
            <nav class="menu"><a href="../home.jsp">Início</a><a href="lista.jsp">Livros</a><a
                    href="../trocas/lista.jsp">Minhas Trocas</a><a href="../usuarios/lista.jsp">Perfil</a></nav>
            <div class="nav-actions"><a class="btn btn-primary" href="form.jsp">+ Cadastrar livro</a></div>
        </div>
    </header>
    <main class="page">
        <div class="container">
            <div class="page-title">
                <h1>Livros disponíveis</h1>
                <div class="muted">Encontre um livro e solicite uma troca.</div>
            </div>
            <div class="panel">
                <div class="toolbar"><input class="input search" placeholder="Pesquisar por título ou autor..."><select
                        class="select" style="max-width:190px">
                        <option>Todos os gêneros</option>
                        <option>Fantasia</option>
                        <option>Romance</option>
                        <option>Tecnologia</option>
                    </select></div>
                <div class="cards">
                    <article class="card">
                        <div class="book-thumb cover-1">O Hobbit</div>
                        <div class="card-body">
                            <h3>O Hobbit</h3>
                            <div class="meta">J. R. R. Tolkien · Fantasia · Bom estado</div><a class="btn btn-primary"
                                href="form.jsp">Ver / editar</a>
                        </div>
                    </article>
                    <article class="card">
                        <div class="book-thumb cover-2">Clean Code</div>
                        <div class="card-body">
                            <h3>Clean Code</h3>
                            <div class="meta">Robert C. Martin · Tecnologia · Ótimo estado</div><a
                                class="btn btn-primary" href="form.jsp">Ver / editar</a>
                        </div>
                    </article>
                    <article class="card">
                        <div class="book-thumb cover-3">Dom Casmurro</div>
                        <div class="card-body">
                            <h3>Dom Casmurro</h3>
                            <div class="meta">Machado de Assis · Romance · Bom estado</div><a class="btn btn-primary"
                                href="form.jsp">Ver / editar</a>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </main>
</body>

</html>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Livro - BookSwap</title>
    <link rel="stylesheet" href="../../../css/estilo.css">
</head>

<body>
    <header class="topbar">
        <div class="container nav"><a class="brand" href="../home.jsp">Book<span>Swap</span></a>
            <nav class="menu"><a href="../home.jsp">Início</a><a href="lista.jsp">Livros</a><a
                    href="../trocas/lista.jsp">Minhas Trocas</a><a href="../usuarios/lista.jsp">Perfil</a></nav>
        </div>
    </header>
    <main class="page">
        <div class="container">
            <div class="page-title">
                <h1>Cadastrar livro</h1>
                <div class="muted">Preencha os dados do livro para disponibilizá-lo para troca.</div>
            </div>
            <div class="panel">
                <form action="#" method="post">
                    <div class="grid-2">
                        <div class="field"><label>Título</label><input class="input" name="titulo" required></div>
                        <div class="field"><label>Autor</label><input class="input" name="autor" required></div>
                        <div class="field"><label>Categoria</label><select class="select" name="categoria">
                                <option>Fantasia</option>
                                <option>Romance</option>
                                <option>Tecnologia</option>
                                <option>Aventura</option>
                                <option>Outro</option>
                            </select></div>
                        <div class="field"><label>Estado</label><select class="select" name="estado">
                                <option>Ótimo</option>
                                <option>Bom</option>
                                <option>Regular</option>
                            </select></div>
                    </div>
                    <div class="field"><label>Descrição</label><textarea class="textarea" name="descricao" rows="5"
                            placeholder="Fale um pouco sobre o livro..."></textarea></div>
                    <div class="actions"><button class="btn btn-primary" type="submit">Salvar livro</button><a
                            class="btn btn-outline" href="lista.jsp">Cancelar</a></div>
                </form>
            </div>
        </div>
    </main>
</body>

</html>
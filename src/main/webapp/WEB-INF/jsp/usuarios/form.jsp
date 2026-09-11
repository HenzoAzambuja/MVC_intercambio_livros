<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Cadastro - BookSwap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>
    <main class="auth">
        <div class="auth-card">
            <div class="brand">Book<span>Swap</span></div>
            <h1>Criar conta</h1>
            <form action="#" method="post">
                <div class="field"><label>Nome</label><input class="input" name="nome" required></div>
                <div class="field"><label>E-mail</label><input class="input" type="email" name="email" required></div>
                <div class="field"><label>Senha</label><input class="input" type="password" name="senha" required></div>
                <button class="btn btn-primary" style="width:100%">Cadastrar</button>
            </form>
            <div class="small">Já possui conta? <a href="../login.jsp" style="color:#365d4a;font-weight:700">Entrar</a>
            </div>
        </div>
    </main>
</body>

</html>
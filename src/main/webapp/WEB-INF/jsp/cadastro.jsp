<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Criar conta - BookSwap</title>

	<link rel="stylesheet"
		  href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<div class="login-page">

	<div class="card login-card">

		<h1>Criar conta</h1>

		<p>
			Cadastre seus dados para começar a trocar livros.
		</p>

		<c:if test="${not empty erro}">
			<div class="alert alert-erro">
				${erro}
			</div>
		</c:if>

		<!-- Envia os dados para o CadastroServlet criar um usuario normal. -->
		<form method="post"
			  action="${pageContext.request.contextPath}/cadastro">

			<!-- Identificacao basica do novo usuario. -->
			<div class="form-group">
				<label for="nome">Nome</label>

				<input type="text"
					   id="nome"
					   name="nome"
					   value="${usuario.nome}"
					   required
					   autofocus>
			</div>

			<div class="form-group">
				<label for="email">Email</label>

				<input type="email"
					   id="email"
					   name="email"
					   value="${usuario.email}"
					   required>
			</div>

			<!-- Credenciais usadas posteriormente na tela de login. -->
			<div class="form-group">
				<label for="login">Login</label>

				<input type="text"
					   id="login"
					   name="login"
					   value="${usuario.login}"
					   required>
			</div>

			<div class="form-group">
				<label for="senha">Senha</label>

				<input type="password"
					   id="senha"
					   name="senha"
					   required>
			</div>

			<div class="actions">
				<button type="submit" class="btn">
					Cadastrar
				</button>

				<a class="btn btn-secondary"
				   href="${pageContext.request.contextPath}/login">
					Voltar ao login
				</a>
			</div>

		</form>

	</div>

</div>

</body>
</html>

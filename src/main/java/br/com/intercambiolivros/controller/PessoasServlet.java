package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.service.LivroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/pessoas")
public class PessoasServlet extends BaseServlet {

    private static final String VIEW = "/WEB-INF/jsp/pessoas.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Usuario usuarioLogado = this.usuarioLogado(req);
        if (usuarioLogado == null) {
            this.redirect(req, resp, "/login");
            return;
        }
        LivroService livroService = new LivroService(this.apiToken(req));
        Long id = this.paramLong(req, "id");

        if (id != null) {
            List<Livro> livros = livroService.listar();
            List<Usuario> donos = pessoasComLivros(livros);
            Usuario usuario = donos.stream()
                    .filter(pessoa -> pessoa.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (usuario == null) {
                this.redirect(req, resp, "/pessoas");
                return;
            }

            req.setAttribute("usuarioPerfil", usuario);
            req.setAttribute("livrosPessoa", livros.stream()
                    .filter(livro -> id.equals(livro.getUsuarioId()))
                    .toList());
        } else {
            List<Usuario> usuarios = pessoasComLivros(livroService.listar()).stream()
                    .filter(usuario -> !usuario.getId().equals(usuarioLogado.getId()))
                    .toList();

            req.setAttribute("usuarios", usuarios);
        }

        this.forward(req, resp, VIEW);
    }

    private Usuario usuarioLogado(HttpServletRequest req) {

    HttpSession session = req.getSession(false);

    if (session == null) {
        return null;
    }

    Object usuario = session.getAttribute("usuarioLogado");

    if (usuario instanceof Usuario) {
        return (Usuario) usuario;
    }

    return null;
}

    private List<Usuario> pessoasComLivros(
            List<Livro> livros) {
        return livros.stream()
                .map(Livro::getUsuario)
                .filter(usuario -> usuario != null && usuario.getId() != null)
                .collect(java.util.stream.Collectors.toMap(
                        Usuario::getId,
                        usuario -> usuario,
                        (atual, ignorado) -> atual,
                        java.util.LinkedHashMap::new))
                .values()
                .stream()
                .toList();
    }
}

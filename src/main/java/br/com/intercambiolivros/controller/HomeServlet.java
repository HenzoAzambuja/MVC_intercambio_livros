package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.model.Troca;
import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.service.LivroService;
import br.com.intercambiolivros.service.TrocaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    private static final String VIEW =
            "/WEB-INF/jsp/home.jsp";

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuarioLogado =
                this.usuarioLogado(req);
        String token = this.apiToken(req);

        if (usuarioLogado == null) {
            this.redirect(req, resp, "/login");
            return;
        }

        LivroService livroService = new LivroService(token);
        List<Livro> livrosDisponiveis =
                livroService.listarDisponiveis(usuarioLogado.getId());

        List<Livro> meusLivros =
                livroService.listarPorUsuario(usuarioLogado.getId());

        List<Troca> trocas =
                new TrocaService(token).listarPorUsuario(usuarioLogado.getId());

        List<Usuario> usuarios = pessoasComLivros(livrosDisponiveis);

        req.setAttribute(
                "livrosDisponiveis",
                this.limitarLivros(
                        livrosDisponiveis,
                        4));

        req.setAttribute(
                "meusLivros",
                this.limitarLivros(
                        meusLivros,
                        4));

        req.setAttribute(
                "usuarios",
                this.limitarUsuarios(
                        usuarios,
                        usuarioLogado.getId(),
                        4));

        req.setAttribute(
                "trocas",
                this.limitarTrocas(
                        trocas,
                        4));

        this.forward(
                req,
                resp,
                VIEW);
    }

    private Usuario usuarioLogado(
            HttpServletRequest req) {

        HttpSession session =
                req.getSession(false);

        if (session == null) {
            return null;
        }

        Object usuario =
                session.getAttribute(
                        "usuarioLogado");

        if (usuario instanceof Usuario) {
            return (Usuario) usuario;
        }

        return null;
    }

    private List<Livro> limitarLivros(
            List<Livro> livros,
            int quantidade) {

        if (livros.size() <= quantidade) {
            return livros;
        }

        return livros.subList(0, quantidade);
    }

    private List<Troca> limitarTrocas(
            List<Troca> trocas,
            int quantidade) {

        if (trocas.size() <= quantidade) {
            return trocas;
        }

        return trocas.subList(0, quantidade);
    }

    private List<Usuario> limitarUsuarios(
            List<Usuario> usuarios,
            Long usuarioLogadoId,
            int quantidade) {

        return usuarios.stream()
                .filter(usuario ->
                        !usuario.getId()
                                .equals(usuarioLogadoId))
                .limit(quantidade)
                .toList();
    }

    private List<Usuario> pessoasComLivros(List<Livro> livros) {
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
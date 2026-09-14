package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.service.LivroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/livros")
public class LivroServlet extends BaseServlet {

    private static final String LISTA =
            "/WEB-INF/jsp/livros/lista.jsp";

    private static final String MEUS =
            "/WEB-INF/jsp/livros/meus.jsp";

    private static final String FORM =
            "/WEB-INF/jsp/livros/form.jsp";

    private final LivroService livroService =
            new LivroService();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuarioLogado =
                this.usuarioLogado(req);

        if (usuarioLogado == null) {
            this.redirect(req, resp, "/login");
            return;
        }

        switch (this.acao(req)) {

            case "novo" ->
                    this.form(req, resp, null);

            case "editar" -> {

                Livro livro =
                        this.livroService.buscarPorId(
                                this.paramLong(req, "id"));

                if (livro == null
                        || !livro.getUsuarioId()
                        .equals(usuarioLogado.getId())) {

                    req.setAttribute(
                            "erro",
                            "Livro nao encontrado.");

                    req.setAttribute(
                            "livros",
                            this.livroService.listarPorUsuario(
                                    usuarioLogado.getId()));

                    this.forward(req, resp, MEUS);
                    return;
                }

                this.form(req, resp, livro);
            }

            case "excluir" -> {

                try {

                    this.livroService.deletar(
                            this.paramLong(req, "id"),
                            usuarioLogado.getId());

                    this.redirect(
                            req,
                            resp,
                            "/livros?acao=meus");

                } catch (IllegalArgumentException e) {

                    req.setAttribute(
                            "erro",
                            e.getMessage());

                    req.setAttribute(
                            "livros",
                            this.livroService.listarPorUsuario(
                                    usuarioLogado.getId()));

                    this.forward(
                            req,
                            resp,
                            MEUS);
                }
            }

            case "meus" -> {

                req.setAttribute(
                        "livros",
                        this.livroService.listarPorUsuario(
                                usuarioLogado.getId()));

                this.forward(req, resp, MEUS);
            }

            default -> {

                req.setAttribute(
                        "livros",
                        this.livroService.listarDisponiveis(
                                usuarioLogado.getId()));

                this.forward(req, resp, LISTA);
            }
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Usuario usuarioLogado =
                this.usuarioLogado(req);

        if (usuarioLogado == null) {
            this.redirect(req, resp, "/login");
            return;
        }

        Livro livro = this.fromRequest(req);

        try {

            this.livroService.salvar(
                    livro,
                    usuarioLogado.getId());

            this.redirect(
                    req,
                    resp,
                    "/livros?acao=meus");

        } catch (IllegalArgumentException e) {

            req.setAttribute(
                    "erro",
                    e.getMessage());

            this.form(req, resp, livro);
        }
    }

    private void form(
            HttpServletRequest req,
            HttpServletResponse resp,
            Livro livro)
            throws ServletException, IOException {

        req.setAttribute("livro", livro);

        this.forward(req, resp, FORM);
    }

    private Livro fromRequest(
            HttpServletRequest req) {

        Livro livro = new Livro();

        livro.setId(
                this.paramLong(req, "id"));

        livro.setTitulo(
                this.param(req, "titulo"));

        livro.setAutor(
                this.param(req, "autor"));

        return livro;
    }

    private Usuario usuarioLogado(
            HttpServletRequest req) {

        HttpSession session =
                req.getSession(false);

        if (session == null) {
            return null;
        }

        Object usuario =
                session.getAttribute("usuarioLogado");

        if (usuario instanceof Usuario) {
            return (Usuario) usuario;
        }

        return null;
    }
}
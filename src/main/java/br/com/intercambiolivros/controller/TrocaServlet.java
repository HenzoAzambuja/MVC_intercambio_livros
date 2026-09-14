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

@WebServlet("/trocas")
public class TrocaServlet extends BaseServlet {

    private static final String LISTA =
            "/WEB-INF/jsp/trocas/lista.jsp";

    private static final String FORM =
            "/WEB-INF/jsp/trocas/form.jsp";

    private final TrocaService trocaService =
            new TrocaService();

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

            case "nova" ->
                    this.form(
                            req,
                            resp,
                            usuarioLogado.getId());

            case "aceitar" ->
                    this.aceitar(
                            req,
                            resp,
                            usuarioLogado.getId());

            case "recusar" ->
                    this.recusar(
                            req,
                            resp,
                            usuarioLogado.getId());

            case "concluir" ->
                    this.concluir(
                            req,
                            resp,
                            usuarioLogado.getId());

            case "concluidas" ->
                    this.concluidas(
                        req,
                        resp,
                        usuarioLogado.getId());

            default ->
                    this.listar(
                            req,
                            resp,
                            usuarioLogado.getId());
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

        try {

            this.trocaService.solicitar(
                    usuarioLogado.getId(),
                    this.paramLong(
                            req,
                            "livroOferecidoId"),
                    this.paramLong(
                            req,
                            "livroRecebidoId"),
                    this.param(
                            req,
                            "mensagem"));

            this.redirect(
                    req,
                    resp,
                    "/trocas");

        } catch (IllegalArgumentException e) {

            req.setAttribute(
                    "erro",
                    e.getMessage());

            this.form(
                    req,
                    resp,
                    usuarioLogado.getId());
        }
    }

    private void listar(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws ServletException, IOException {

        List<Troca> trocas =
                this.trocaService.listarPorUsuario(
                        usuarioId);

        req.setAttribute(
                "trocas",
                trocas);

        this.forward(
                req,
                resp,
                LISTA);
    }

    private void form(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws ServletException, IOException {

        List<Livro> meusLivros =
                this.livroService.listarPorUsuario(
                        usuarioId);

        List<Livro> livrosDisponiveis =
                this.livroService.listarDisponiveis(
                        usuarioId);

        req.setAttribute(
                "meusLivros",
                meusLivros);

        req.setAttribute(
                "livrosDisponiveis",
                livrosDisponiveis);

        this.forward(
                req,
                resp,
                FORM);
    }

    private void aceitar(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws IOException {

        try {

            this.trocaService.aceitar(
                    this.paramLong(req, "id"),
                    usuarioId);

        } catch (IllegalArgumentException e) {
            req.getSession()
                    .setAttribute(
                            "erroTroca",
                            e.getMessage());
        }

        this.redirect(
                req,
                resp,
                "/trocas");
    }

    private void recusar(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws IOException {

        try {

            this.trocaService.recusar(
                    this.paramLong(req, "id"),
                    usuarioId);

        } catch (IllegalArgumentException e) {
            req.getSession()
                    .setAttribute(
                            "erroTroca",
                            e.getMessage());
        }

        this.redirect(
                req,
                resp,
                "/trocas");
    }

    private void concluir(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws IOException {

        try {

            this.trocaService.concluir(
                    this.paramLong(req, "id"),
                    usuarioId);

        } catch (IllegalArgumentException e) {
            req.getSession()
                    .setAttribute(
                            "erroTroca",
                            e.getMessage());
        }

        this.redirect(
                req,
                resp,
                "/trocas");
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

    private void concluidas(
            HttpServletRequest req,
            HttpServletResponse resp,
            Long usuarioId)
            throws ServletException, IOException {

        req.setAttribute(
                "trocasConcluidas",
                this.trocaService
                        .listarConcluidasPorUsuario(
                                usuarioId));

        this.forward(
                req,
                resp,
                "/WEB-INF/jsp/trocas/concluidas.jsp");
    }

}
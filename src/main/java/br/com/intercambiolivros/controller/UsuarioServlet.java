package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.service.PerfilService;
import br.com.intercambiolivros.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller de Usuario.
 *
 * Faz a ponte entre HTTP, Service e View.
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/usuarios/lista.jsp";
    private static final String FORM = "/WEB-INF/jsp/usuarios/form.jsp";

    private final UsuarioService usuarioService = new UsuarioService();
    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {

            case "novo":
                this.form(req, resp, null);
                break;

            case "editar":
                Usuario usuario = this.usuarioService.buscarPorId(
                        this.paramLong(req, "id"));

                this.form(req, resp, usuario);
                break;

            case "excluir":
                try {
                    this.usuarioService.deletar(
                            this.paramLong(req, "id"));

                    this.redirect(req, resp, "/usuarios");

                } catch (IllegalArgumentException e) {

                    req.setAttribute("erro", e.getMessage());
                    req.setAttribute("usuarios",
                            this.usuarioService.listar());

                    this.forward(req, resp, LISTA);
                }
                break;

            default:
                req.setAttribute("usuarios",
                        this.usuarioService.listar());

                this.forward(req, resp, LISTA);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Usuario usuario = this.fromRequest(req);

        try {
            this.usuarioService.salvar(usuario);

            this.redirect(req, resp, "/usuarios");

        } catch (IllegalArgumentException e) {

            req.setAttribute("erro", e.getMessage());

            this.form(req, resp, usuario);
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp,
                      Usuario usuario)
            throws ServletException, IOException {

        if ("editar".equals(this.acao(req)) && usuario == null) {
            this.redirect(req, resp, "/usuarios");
            return;
        }

        req.setAttribute("usuario", usuario);
        req.setAttribute("perfis", this.perfilService.listar());

        this.forward(req, resp, FORM);
    }

    private Usuario fromRequest(HttpServletRequest req) {

        Usuario usuario = new Usuario();

        usuario.setId(this.paramLong(req, "id"));
        usuario.setNome(this.param(req, "nome"));
        usuario.setEmail(this.param(req, "email"));
        usuario.setLogin(this.param(req, "login"));
        usuario.setSenha(this.param(req, "senha"));
        usuario.setPerfilId(this.paramLong(req, "perfilId"));

        return usuario;
    }
}
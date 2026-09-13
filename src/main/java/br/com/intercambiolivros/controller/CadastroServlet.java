package br.com.intercambiolivros.controller;

import java.io.IOException;
import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro")
public class CadastroServlet extends BaseServlet {

    private static final String VIEW =
            "/WEB-INF/jsp/cadastro.jsp";

    private final UsuarioService usuarioService =
            new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        this.forward(req, resp, VIEW);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Usuario usuario = new Usuario();
        usuario.setNome(this.param(req, "nome"));
        usuario.setEmail(this.param(req, "email"));
        usuario.setLogin(this.param(req, "login"));
        usuario.setSenha(this.param(req, "senha"));

        try {
            this.usuarioService.cadastrarNormal(usuario);
            this.redirect(req, resp, "/login");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            req.setAttribute("usuario", usuario);
            this.forward(req, resp, VIEW);
        }
    }
}
package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.service.LivroService;
import br.com.intercambiolivros.service.UsuarioService;
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

    private final UsuarioService usuarioService = new UsuarioService();
    private final LivroService livroService = new LivroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuarioLogado = this.usuarioLogado(req);
        Long id = this.paramLong(req, "id");

        if (id != null) {
            Usuario usuario = this.usuarioService.buscarPorId(id);

            if (usuario == null) {
                this.redirect(req, resp, "/pessoas");
                return;
            }

            req.setAttribute("usuarioPerfil", usuario);
            req.setAttribute("livrosPessoa", this.livroService.listarPorUsuario(id));
        } else {
            List<Usuario> usuarios = this.usuarioService.listar().stream()
                    .filter(usuario -> !usuario.getId().equals(usuarioLogado.getId()))
                    .toList();

            req.setAttribute("usuarios", usuarios);
        }

        this.forward(req, resp, VIEW);
    }

    private Usuario usuarioLogado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (Usuario) session.getAttribute("usuarioLogado");
    }
}

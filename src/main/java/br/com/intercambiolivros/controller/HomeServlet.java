package br.com.intercambiolivros.controller;

import br.com.intercambiolivros.model.Livro;
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

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    private static final String VIEW = "/WEB-INF/jsp/home.jsp";
    private final LivroService livroService = new LivroService();
    private final UsuarioService usuarioService =new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

        Usuario usuarioLogado = this.usuarioLogado(req);

        if (usuarioLogado == null) {
            this.redirect(req, resp, "/login");
            return;
        }

        List<Livro> livrosDisponiveis = this.livroService.listarDisponiveis(usuarioLogado.getId());

        List<Livro> meusLivros = this.livroService.listarPorUsuario(usuarioLogado.getId());

        List<Usuario> usuarios = this.usuarioService.listar();

        req.setAttribute("livrosDisponiveis",this.limitar(livrosDisponiveis, 4));

        req.setAttribute("meusLivros",this.limitar(meusLivros, 4));

        req.setAttribute("usuarios",this.limitarUsuarios(usuarios,usuarioLogado.getId(),4));

        this.forward(req, resp, VIEW);
    }

    private Usuario usuarioLogado(HttpServletRequest req) {

        HttpSession session =req.getSession(false);

        if (session == null) {
            return null;
        }

        Object usuario = session.getAttribute("usuarioLogado");

        if (usuario instanceof Usuario) {
            return (Usuario) usuario;
        }

        return null;
    }

    private List<Livro> limitar(List<Livro> livros,int quantidade) {

        if (livros.size() <= quantidade) {
            return livros;
        }

        return livros.subList(0, quantidade);
    }

    private List<Usuario> limitarUsuarios(
            List<Usuario> usuarios,
            Long usuarioLogadoId,
            int quantidade) {

        List<Usuario> resultado = usuarios.stream().filter(usuario ->!usuario.getId().equals(usuarioLogadoId)).limit(quantidade).toList();

        return resultado;
    }
}

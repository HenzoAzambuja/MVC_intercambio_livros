package br.com.intercambiolivros.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.config.ApiDAO;

@WebFilter(urlPatterns = {"/home", "/livros", "/livros/*", "/trocas", "/trocas/*", "/usuarios", "/usuarios/*", "/pessoas", "/pessoas/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Usuario usuarioLogado = session == null
            ? null
            : (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String token = (String) session.getAttribute("jwtToken");

        if (token == null || token.isBlank()) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String caminho = req.getRequestURI().substring(req.getContextPath().length());

        boolean rotaRestrita =
                caminho.startsWith("/usuarios")
                || caminho.startsWith("/pessoas");

        if (rotaRestrita && !Long.valueOf(1L).equals(usuarioLogado.getPerfilId())) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        ApiDAO.definirTokenApi(token);

        try {
            chain.doFilter(request, response);
        } finally {
            ApiDAO.limparTokenApi();
        }
    }
}

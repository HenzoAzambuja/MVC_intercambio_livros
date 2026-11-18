package br.com.intercambiolivros.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller de Perfil.
 * Ponte entre rota, service e view — sem regra de negocio.
 */
@WebServlet("/perfis")
public class PerfilServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirect(req, resp, "/usuarios");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.redirect(req, resp, "/usuarios");
    }
}

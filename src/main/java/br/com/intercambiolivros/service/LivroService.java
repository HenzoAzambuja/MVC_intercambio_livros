package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.LivroDAO;
import br.com.intercambiolivros.model.Livro;

import java.util.List;

public class LivroService {

    private final LivroDAO livroDAO;

    public LivroService() {
        this.livroDAO = new LivroDAO();
    }

    public List<Livro> listarDisponiveis(Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.livroDAO.listarDisponiveis(usuarioId);
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.livroDAO.listarPorUsuario(usuarioId);
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.livroDAO.listarDisponiveisPorUsuario(usuarioId);
    }

    public Livro buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        return this.livroDAO.buscarPorId(id);
    }

    private void validarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado."
            );
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Id do livro invalido."
            );
        }
    }

    public void alterarDisponibilidade(Long id, Long usuarioId) {
        this.validarId(id);
        this.validarUsuario(usuarioId);
        this.livroDAO.alterarDisponibilidade(id);
    }

    public void salvar(Livro livro, Long usuarioId) {
        if (livro == null) {
            throw new IllegalArgumentException("Livro e obrigatorio.");
        }

        this.validarUsuario(usuarioId);
        this.prepararDados(livro);
        this.validarCamposObrigatorios(livro);

        if (livro.getId() == null) {
            this.livroDAO.inserir(livro);
        } else {
            this.livroDAO.alterar(livro);
        }
    }

    public void deletar(Long id, Long usuarioId) {
        this.validarId(id);
        this.validarUsuario(usuarioId);
        this.livroDAO.deletar(id);
    }

    private void prepararDados(Livro livro) {

        livro.setTitulo(
                this.normalizar(livro.getTitulo()));

        livro.setAutor(
                this.normalizar(livro.getAutor()));
    }

    private void validarCamposObrigatorios(Livro livro) {

        if (livro.getTitulo() == null) {
            throw new IllegalArgumentException(
                    "Titulo e obrigatorio.");
        }

        if (livro.getAutor() == null) {
            throw new IllegalArgumentException(
                    "Autor e obrigatorio.");
        }
    }

    private String normalizar(String valor) {

        if (valor == null) {
            return null;
        }

        String limpo = valor.trim();

        return limpo.isEmpty() ? null : limpo;
    }
}
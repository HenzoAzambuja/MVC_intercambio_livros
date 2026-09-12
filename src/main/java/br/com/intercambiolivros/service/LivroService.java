package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.LivroDAO;
import br.com.intercambiolivros.model.Livro;

import java.util.List;

public class LivroService {

    private final LivroDAO livroDAO;

    public LivroService() {
        this.livroDAO = new LivroDAO();
    }

    public List<Livro> listar() {
        return this.livroDAO.listarTodos();
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

    public Livro buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        return this.livroDAO.buscarPorId(id);
    }

    public void salvar(Livro livro, Long usuarioId) {

        if (livro == null) {
            throw new IllegalArgumentException(
                    "Livro e obrigatorio.");
        }

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        this.prepararDados(livro);
        this.validarCamposObrigatorios(livro);

        if (livro.getId() == null) {

            livro.setUsuarioId(usuarioId);
            this.livroDAO.inserir(livro);

            return;
        }

        Livro existente =
                this.livroDAO.buscarPorId(livro.getId());

        if (existente == null) {
            throw new IllegalArgumentException(
                    "Livro nao encontrado.");
        }

        if (!existente.getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException(
                    "Voce nao pode alterar este livro.");
        }

        livro.setUsuarioId(usuarioId);
        this.livroDAO.alterar(livro);
    }

    public void deletar(Long id, Long usuarioId) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "Id e obrigatorio para excluir.");
        }

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        Livro livro = this.livroDAO.buscarPorId(id);

        if (livro == null) {
            throw new IllegalArgumentException(
                    "Livro nao encontrado.");
        }

        if (!livro.getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException(
                    "Voce nao pode excluir este livro.");
        }

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
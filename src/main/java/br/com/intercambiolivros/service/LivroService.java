package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.LivroDAO;
import br.com.intercambiolivros.model.Livro;

import java.util.List;

public class LivroService {

    private final LivroDAO livroDAO;

    public LivroService(String token) {
        this.livroDAO = new LivroDAO(token);
    }

    public List<Livro> listar() {
        return this.livroDAO.listarTodos();
    }

    public List<Livro> listarDisponiveis(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("Usuario nao identificado.");
        }
        return this.livroDAO.listarDisponiveis(usuarioId);
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("Usuario nao identificado.");
        }
        return this.livroDAO.listarPorUsuario(usuarioId);
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("Usuario nao identificado.");
        }
        return this.livroDAO.listarDisponiveisPorUsuario(usuarioId);
    }

    public Livro buscarPorId(Long id) {
        return this.livroDAO.buscarPorId(id);
    }

    public void alterarDisponibilidade(Long id, Long usuarioId) {
        if (id == null || usuarioId == null) {
            throw new IllegalArgumentException("Livro e usuario sao obrigatorios.");
        }
        this.livroDAO.alterarDisponibilidade(id, false);
    }

    public void salvar(Livro livro, Long usuarioId) {
        if (livro == null || usuarioId == null) {
            throw new IllegalArgumentException("Livro e usuario sao obrigatorios.");
        }
        livro.setTitulo(normalizar(livro.getTitulo()));
        livro.setAutor(normalizar(livro.getAutor()));
        if (livro.getTitulo() == null || livro.getAutor() == null) {
            throw new IllegalArgumentException("Titulo e autor sao obrigatorios.");
        }
        livro.setUsuarioId(usuarioId);
        if (livro.getId() == null) {
            this.livroDAO.inserir(livro);
        } else {
            this.livroDAO.alterar(livro);
        }
    }

    public void deletar(Long id, Long usuarioId) {
        if (id == null || usuarioId == null) {
            throw new IllegalArgumentException("Livro e usuario sao obrigatorios.");
        }
        this.livroDAO.deletar(id);
    }

    private static String normalizar(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}

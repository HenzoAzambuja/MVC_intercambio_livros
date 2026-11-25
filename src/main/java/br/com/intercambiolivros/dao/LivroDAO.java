package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiDAO;
import br.com.intercambiolivros.model.Livro;

import java.util.List;

public class LivroDAO extends ApiDAO {

    public List<Livro> listarDisponiveis(Long usuarioId) {
        return apiGetLista("/api/livros", Livro.class);
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {
        return apiGetLista("/api/livros/usuario", Livro.class);
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {
        return apiGetLista(
                "/api/livros/usuario/disponiveis",
                Livro.class
        );
    }

    public Livro buscarPorId(Long id) {
        try {
            return apiGet("/api/livros/" + id, Livro.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void inserir(Livro livro) {
        apiPost("/api/livros", livro);
    }

    public void alterar(Livro livro) {
        apiPut("/api/livros/" + livro.getId(), livro);
    }

    public void alterarDisponibilidade(Long id) {
        apiPut("/api/livros/" + id + "/disponibilidade", null);
    }

    public void deletar(Long id) {
        apiDelete("/api/livros/" + id);
    }
}
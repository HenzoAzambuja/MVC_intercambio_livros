package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiSingleton;
import br.com.intercambiolivros.config.ApiException;
import br.com.intercambiolivros.model.Livro;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class LivroDAO {

    private static final Type LIVRO_LIST_TYPE =
            TypeToken.getParameterized(List.class, Livro.class).getType();

    private final ApiSingleton api = ApiSingleton.getInstance();
    private final String token;

    public LivroDAO(String token) {
        this.token = token;
    }

    public List<Livro> listarTodos() {
        return this.api.get("/livros", this.token, LIVRO_LIST_TYPE);
    }

    public List<Livro> listarDisponiveis(Long usuarioId) {
        return this.listarTodos();
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {
        return this.api.get("/livros/usuario", this.token, LIVRO_LIST_TYPE);
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {
        return this.listarTodos().stream()
                .filter(livro -> usuarioId.equals(livro.getUsuarioId()))
                .toList();
    }

    public Livro buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return this.api.get("/livros/" + id, this.token, Livro.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void inserir(Livro livro) {
        this.api.post("/livros", this.token, livro, Void.class);
    }

    public void alterar(Livro livro) {
        this.api.put("/livros/" + livro.getId(), this.token, livro, Void.class);
    }

    public void alterarDisponibilidade(Long id, boolean disponivel) {
        this.api.put(
                "/livros/" + id + "/disponibilidade",
                this.token,
                null,
                Void.class);
    }

    public void deletar(Long id) {
        this.api.delete("/livros/" + id, this.token, Void.class);
    }
}

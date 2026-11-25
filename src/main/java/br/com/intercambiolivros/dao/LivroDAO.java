package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.model.Livro;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.List;

public class LivroDAO extends ApiDAO {

    public List<Livro> listarDisponiveis(Long usuarioId) {
        return getLista("/api/livros", Livro.class);
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {
        return getLista("/api/livros/usuario", Livro.class);
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {
        return getLista("/api/livros/usuario/disponiveis", Livro.class);
    }

    public Livro buscarPorId(Long id) {
        try {
            return get("/api/livros/" + id, Livro.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void inserir(Livro livro) {
        post("/api/livros", livro);
    }

    public void alterar(Livro livro) {
        put("/api/livros/" + livro.getId(), livro);
    }

    public void alterarDisponibilidade(Long id) {
        put("/api/livros/" + id + "/disponibilidade", null);
    }

    public void deletar(Long id) {
        executar(() -> api.delete("/api/livros/" + id));
    }

    private <T> List<T> getLista(String caminho, Class<T> tipoItem) {
        Type tipoLista =
                TypeToken.getParameterized(List.class, tipoItem).getType();

        return converter(executar(() -> api.get(caminho)), tipoLista);
    }

    private <T> T get(String caminho, Class<T> tipo) {
        return converter(
                executar(() -> api.get(caminho)),
                tipo
        );
    }

    private void post(String caminho, Object dados) {
        String json = dados == null ? null : gson.toJson(dados);
        verificarResposta(executar(() -> api.post(caminho, json)));
    }

    private void put(String caminho, Object dados) {
        String json = dados == null ? null : gson.toJson(dados);
        verificarResposta(executar(() -> api.put(caminho, json)));
    }

    private <T> T converter(HttpResponse<String> resposta, Type tipo) {
        verificarResposta(resposta);

        if (resposta.statusCode() == 204
                || resposta.body() == null
                || resposta.body().isBlank()) {
            return null;
        }

        return gson.fromJson(resposta.body(), tipo);
    }

    private void verificarResposta(HttpResponse<String> resposta) {
        if (resposta.statusCode() < 200 || resposta.statusCode() >= 300) {
            throw new ApiException(resposta.statusCode(), resposta.body());
        }
    }

    private HttpResponse<String> executar(ChamadaApi chamada) {
        try {
            return chamada.executar();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Chamada à API interrompida.", e);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível conectar à API.", e
            );
        }
    }

    @FunctionalInterface
    private interface ChamadaApi {
        HttpResponse<String> executar()
                throws IOException, InterruptedException;
    }

    private static class ApiException extends RuntimeException {
        private final int statusCode;

        private ApiException(int statusCode, String resposta) {
            super("API respondeu HTTP " + statusCode + ": " + resposta);
            this.statusCode = statusCode;
        }

        private int getStatusCode() {
            return statusCode;
        }
    }
}
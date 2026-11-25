package br.com.intercambiolivros.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public abstract class ApiDAO {

    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static final ThreadLocal<String> TOKEN_API = new ThreadLocal<>();
    private static final String API_BASE_URL = obterApiBaseUrl();

    public static void definirTokenApi(String token) {
        if (token == null || token.isBlank()) {
            TOKEN_API.remove();
        } else {
            TOKEN_API.set(token);
        }
    }

    public static void limparTokenApi() {
        TOKEN_API.remove();
    }

    protected <T> T apiGet(String caminho, Class<T> tipoResposta) {
        return requisitar("GET", caminho, null, tipoResposta);
    }

    protected <T> List<T> apiGetLista(String caminho, Class<T> tipoItem) {
        Type tipoLista =
                TypeToken.getParameterized(List.class, tipoItem).getType();

        return requisitar("GET", caminho, null, tipoLista);
    }

    protected void apiPost(String caminho, Object dados) {
        requisitar("POST", caminho, dados, Void.class);
    }

    protected void apiPut(String caminho, Object dados) {
        requisitar("PUT", caminho, dados, Void.class);
    }

    protected void apiDelete(String caminho) {
        requisitar("DELETE", caminho, null, Void.class);
    }

    private <T> T requisitar(
            String metodo,
            String caminho,
            Object dados,
            Type tipoResposta) {

        HttpRequest.BodyPublisher corpo = dados == null
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(GSON.toJson(dados));

        HttpRequest.Builder construtor = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + caminho))
                .header("Accept", "application/json")
                .method(metodo, corpo);

        if (dados != null) {
            construtor.header("Content-Type", "application/json");
        }

        String token = TOKEN_API.get();
        if (token != null && !token.isBlank()) {
            construtor.header("Authorization", "Bearer " + token);
        }

        try {
            HttpResponse<String> resposta = HTTP.send(
                    construtor.build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            if (resposta.statusCode() < 200
                    || resposta.statusCode() >= 300) {
                throw new ApiException(
                        resposta.statusCode(),
                        resposta.body()
                );
            }

            if (resposta.statusCode() == 204
                    || resposta.body() == null
                    || resposta.body().isBlank()
                    || tipoResposta == Void.class) {
                return null;
            }

            return GSON.fromJson(resposta.body(), tipoResposta);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Chamada à API interrompida.", e
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível conectar à API.", e
            );
        }
    }

    private static String obterApiBaseUrl() {
        String url = System.getenv("API_BASE_URL");

        if (url == null || url.isBlank()) {
            url = "http://localhost:8080/intercambiolivros";
        }

        return url.replaceAll("/+$", "");
    }

    protected static class ApiException extends RuntimeException {

        private final int statusCode;

        protected ApiException(int statusCode, String resposta) {
            super("API respondeu HTTP " + statusCode + ": " + resposta);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}
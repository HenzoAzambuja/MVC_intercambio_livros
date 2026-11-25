package br.com.intercambiolivros.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiSingleton {

    private static final String BASE_URL = obterApiBaseUrl();

    private static ApiSingleton instance;

    private final HttpClient client;
    private final Gson gson;

    private static final ThreadLocal<String> tokenContext =
            new ThreadLocal<>();

    private ApiSingleton() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.gson = new GsonBuilder().create();
    }

    public static synchronized ApiSingleton getInstance() {
        if (instance == null) {
            instance = new ApiSingleton();
        }
        return instance;
    }

    public static void setToken(String token) {
        if (token == null || token.isBlank()) {
            tokenContext.remove();
        } else {
            tokenContext.set(token);
        }
    }

    public static void clearToken() {
        tokenContext.remove();
    }

    public Gson getGson() {
        return gson;
    }

    public HttpResponse<String> get(String endpoint)
            throws IOException, InterruptedException {
        HttpRequest request = criarRequisicao(endpoint)
                .GET()
                .build();

        return enviar(request);
    }

    public HttpResponse<String> post(String endpoint, String jsonBody)
            throws IOException, InterruptedException {
        HttpRequest request = criarRequisicao(endpoint)
                .header("Content-Type", "application/json")
                .POST(criarCorpo(jsonBody))
                .build();

        return enviar(request);
    }

    public HttpResponse<String> put(String endpoint, String jsonBody)
            throws IOException, InterruptedException {
        HttpRequest request = criarRequisicao(endpoint)
                .header("Content-Type", "application/json")
                .PUT(criarCorpo(jsonBody))
                .build();

        return enviar(request);
    }

    public HttpResponse<String> delete(String endpoint)
            throws IOException, InterruptedException {
        HttpRequest request = criarRequisicao(endpoint)
                .DELETE()
                .build();

        return enviar(request);
    }

    private HttpRequest.Builder criarRequisicao(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json");

        String token = tokenContext.get();
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }

        return builder;
    }

    private HttpResponse<String> enviar(HttpRequest request)
            throws IOException, InterruptedException {
        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private HttpRequest.BodyPublisher criarCorpo(String jsonBody) {
        if (jsonBody == null) {
            return HttpRequest.BodyPublishers.noBody();
        }

        return HttpRequest.BodyPublishers.ofString(jsonBody);
    }

    private static String obterApiBaseUrl() {
        String url = System.getenv("API_BASE_URL");

        if (url == null || url.isBlank()) {
            url = "http://localhost:8080/intercambiolivros";
        }

        return url.replaceAll("/+$", "");
    }
}
package br.com.intercambiolivros.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class ApiSingleton {

    private static final String BASE_URL =
            "http://host.docker.internal:8080/intercambiolivros/api";

    private static final ApiSingleton INSTANCE = new ApiSingleton();

    private final String baseUrl;
    private final Gson gson;
    private final HttpClient client;

    
    private ApiSingleton() {
        
        this.client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        this.gson = new Gson();
        this.baseUrl = BASE_URL;
    }

    public static ApiSingleton getInstance() {
        return INSTANCE;
    }

    // Metodos de requisicao HTTP
    public <T> T get(String path, String token, Type responseType) {
        return this.request("GET", path, token, null, responseType);
    }

    public <T> T post(String path, String token, Object body, Type responseType) {
        return this.request("POST", path, token, body, responseType);
    }

    public <T> T put(String path, String token, Object body, Type responseType) {
        return this.request("PUT", path, token, body, responseType);
    }

    public <T> T delete(String path, String token, Type responseType) {
        return this.request("DELETE", path, token, null, responseType);
    }

    private <T> T request(
            String method,
            String path,
            String token,
            Object body,
            Type responseType) {

        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(this.baseUrl + path))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json");

        if (token != null && !token.isBlank()) {
            request.header("Authorization", "Bearer " + token);
        }

        if (body != null) {
            request.header("Content-Type", "application/json; charset=UTF-8")
                    .method(method, HttpRequest.BodyPublishers.ofString(
                            this.gson.toJson(body)));
        } else {
            request.method(method, HttpRequest.BodyPublishers.noBody());
        }

        try {
            //mandando a requisicao e recebendo a resposta
            HttpResponse<String> response = this.client.send(
                    request.build(),
                    HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            //erro na resposta da API
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                String message =
                        this.errorMessage(responseBody, response.statusCode());
                throw new ApiException(response.statusCode(), message);
            }

            // resposta vazia
            if (responseType == Void.class || responseBody == null
                    || responseBody.isBlank()) {
                return null;
            }
            return this.gson.fromJson(responseBody, responseType);
        } catch (IOException e) {
            throw new ApiException(
                    0,
                    "Nao foi possivel conectar a API. Verifique a URL e a disponibilidade do servico.",
                    e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException(0, "A comunicacao com a API foi interrompida.", e);
        }
    }

    private String errorMessage(String responseBody, int statusCode) {
        if (responseBody != null && !responseBody.isBlank()) {
            try {
                JsonElement json = JsonParser.parseString(responseBody);
                if (json.isJsonObject()) {
                    JsonObject object = json.getAsJsonObject();
                    for (String key : new String[]{"erro", "mensagem", "message"}) {
                        JsonElement value = object.get(key);
                        if (value != null && value.isJsonPrimitive()) {
                            return value.getAsString();
                        }
                    }
                }
            } catch (JsonParseException e) {
                return "A API retornou HTTP " + statusCode + ".";
            }
        }
        return "A API retornou HTTP " + statusCode + ".";
    }
}

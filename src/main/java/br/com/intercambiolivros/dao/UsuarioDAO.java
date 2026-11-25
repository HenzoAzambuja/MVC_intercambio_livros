package br.com.intercambiolivros.dao;

import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import br.com.intercambiolivros.config.ApiDAO;
import br.com.intercambiolivros.model.Usuario;
import br.com.intercambiolivros.model.Perfil;

/**
 * DAO de Usuario.
 *
 * Responsavel pelo acesso ao banco de dados.
 */
public class UsuarioDAO extends ApiDAO {

    public UsuarioDAO() {
        super();
    }

    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    private static final String API_BASE_URL = obterApiBaseUrl();

    private static String obterApiBaseUrl() {
        String url = System.getenv("API_BASE_URL");

        if (url == null || url.isBlank()) {
            url = "http://localhost:8080/intercambiolivros";
        }

        return url.replaceAll("/+$", "");
    }

    public Usuario buscarPorLoginESenha(String login, String senha) {
        LoginRequest dados = new LoginRequest();
        dados.login = login;
        dados.senha = senha;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(dados)))
                .build();

        try {
            HttpResponse<String> response = HTTP.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 401) {
                return null;
            }

            if (response.statusCode() != 200) {
                ApiError erro = GSON.fromJson(response.body(), ApiError.class);
                String mensagem = erro == null || erro.erro == null
                        ? "Falha na autenticação pela API."
                        : erro.erro;

                if (response.statusCode() >= 400
                        && response.statusCode() < 500) {
                    throw new IllegalArgumentException(mensagem);
                }

                throw new IllegalStateException(mensagem);
            }

            LoginResponse resultado =
                    GSON.fromJson(response.body(), LoginResponse.class);

            if (resultado == null
                    || resultado.token == null
                    || resultado.usuarioId == null) {
                throw new IllegalStateException(
                        "A API retornou uma resposta de login incompleta."
                );
            }

            Usuario usuario = new Usuario();
            usuario.setId(resultado.usuarioId);
            usuario.setNome(resultado.nome);
            usuario.setLogin(login);
            usuario.setPerfilId(resultado.perfilId);
            usuario.setAtivo(true);
            usuario.setToken(resultado.token);

            Perfil perfil = new Perfil();
            perfil.setId(resultado.perfilId);
            usuario.setPerfil(perfil);

            return usuario;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "A chamada de autenticação foi interrompida.", e
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível conectar à API para autenticar.", e
            );
        }
    }

    public List<Usuario> listarTodos() {

        return apiGetLista("/api/usuarios", Usuario.class);
    }

    public Usuario buscarPorId(Long id) {
        try {
            return apiGet("/api/usuarios/" + id, Usuario.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void inserir(Usuario usuario) {
        apiPost("/api/usuarios/cadastro", usuario);
    }

    public void alterar(Usuario usuario) {
        apiPut("/api/usuarios/" + usuario.getId(), usuario);
    }

    public void deletar(Long id) {
        apiDelete("/api/usuarios/" + id);
    }

    private static class LoginRequest {
        private String login;
        private String senha;
    }

    private static class LoginResponse {
        private String token;
        private Long usuarioId;
        private String nome;
        private Long perfilId;
    }

    private static class ApiError {
        private String erro;
    }

}
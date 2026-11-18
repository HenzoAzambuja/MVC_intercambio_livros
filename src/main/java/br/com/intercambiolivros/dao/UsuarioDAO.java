package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiSingleton;
import br.com.intercambiolivros.config.ApiException;
import br.com.intercambiolivros.model.Perfil;
import br.com.intercambiolivros.model.Usuario;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class UsuarioDAO {

    private static final Type USUARIO_LIST_TYPE =
            TypeToken.getParameterized(List.class, Usuario.class).getType();

    private final ApiSingleton api = ApiSingleton.getInstance();
    private final String token;

    public UsuarioDAO(String token) {
        this.token = token;
    }

    public Usuario buscarPorLoginESenha(String login, String senha) {
        JsonObject response = this.api.post(
                "/auth/login",
                null,
                Map.of("login", login, "senha", senha),
                JsonObject.class);

        Usuario usuario = new Usuario();
        usuario.setId(response.get("usuarioId").getAsLong());
        usuario.setNome(response.get("nome").getAsString());
        usuario.setPerfilId(response.get("perfilId").getAsLong());
        usuario.setAtivo(true);
        usuario.setPerfil(perfil(usuario.getPerfilId()));
        usuario.setApiToken(response.get("token").getAsString());
        return usuario;
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios =
                this.api.get("/usuarios", this.token, USUARIO_LIST_TYPE);
        usuarios.forEach(usuario -> usuario.setPerfil(perfil(usuario.getPerfilId())));
        return usuarios;
    }

    public Usuario buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        try {
            Usuario usuario = this.api.get(
                    "/usuarios/" + id,
                    this.token,
                    Usuario.class);
            usuario.setPerfil(perfil(usuario.getPerfilId()));
            return usuario;
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public void inserir(Usuario usuario) {
        if (!Long.valueOf(2L).equals(usuario.getPerfilId())) {
            throw new IllegalArgumentException(
                    "A API atual permite cadastrar somente usuarios Cliente.");
        }
        this.api.post("/usuarios/cadastro", this.token, usuario, Void.class);
    }

    public void alterar(Usuario usuario) {
        this.api.put(
                "/usuarios/" + usuario.getId(),
                this.token,
                usuario,
                Void.class);
    }

    public void deletar(Long id) {
        this.api.delete("/usuarios/" + id, this.token, Void.class);
    }

    private static Perfil perfil(Long perfilId) {
        if (perfilId == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(perfilId);
        perfil.setNome(Long.valueOf(1L).equals(perfilId)
                ? "Administrador"
                : "Cliente");
        return perfil;
    }

}

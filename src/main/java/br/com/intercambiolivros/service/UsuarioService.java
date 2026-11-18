package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.UsuarioDAO;
import br.com.intercambiolivros.model.Usuario;

import java.util.List;

public class UsuarioService {

    private static final int SENHA_MINIMA = 6;
    private static final long PERFIL_NORMAL_ID = 2L;

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this(null);
    }

    public UsuarioService(String token) {
        this.usuarioDAO = new UsuarioDAO(token);
    }

    public Usuario autenticar(String login, String senha) {
        login = normalizar(login);
        if (login == null || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Informe login e senha.");
        }
        return this.usuarioDAO.buscarPorLoginESenha(login, senha);
    }

    public List<Usuario> listar() {
        return this.usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {
        return this.usuarioDAO.buscarPorId(id);
    }

    public void salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario e obrigatorio.");
        }
        usuario.setNome(normalizar(usuario.getNome()));
        usuario.setEmail(normalizar(usuario.getEmail()));
        usuario.setLogin(normalizar(usuario.getLogin()));
        usuario.setSenha(normalizar(usuario.getSenha()));
        validarObrigatorio(usuario);
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Informe um email valido.");
        }
        if (usuario.getSenha().length() < SENHA_MINIMA) {
            throw new IllegalArgumentException(
                    "Senha deve ter no minimo " + SENHA_MINIMA + " caracteres.");
        }
        if (usuario.getId() == null) {
            this.usuarioDAO.inserir(usuario);
        } else {
            this.usuarioDAO.alterar(usuario);
        }
    }

    public void cadastrarNormal(Usuario usuario) {
        usuario.setId(null);
        usuario.setPerfilId(PERFIL_NORMAL_ID);
        this.salvar(usuario);
    }

    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        this.usuarioDAO.deletar(id);
    }

    private static void validarObrigatorio(Usuario usuario) {
        if (usuario.getNome() == null
                || usuario.getEmail() == null
                || usuario.getLogin() == null
                || usuario.getSenha() == null
                || usuario.getPerfilId() == null) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatorios.");
        }
    }

    private static String normalizar(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}

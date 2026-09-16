package br.com.intercambiolivros.service;

import java.util.List;

import br.com.intercambiolivros.dao.PerfilDAO;
import br.com.intercambiolivros.dao.UsuarioDAO;
import br.com.intercambiolivros.model.Usuario;


/**
 * Service de Usuario.
 *
 * Responsavel pelas regras de negocio.
 */
public class UsuarioService {

    private static final int SENHA_MINIMA = 6;
    private static final long PERFIL_NORMAL_ID = 2L;

    private final UsuarioDAO usuarioDAO;
    private final PerfilDAO perfilDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.perfilDAO = new PerfilDAO();
    }


    public Usuario autenticar(String login, String senha) {

        login = this.normalizar(login);
        senha = this.normalizar(senha);

        if (login == null || senha == null) {
            throw new IllegalArgumentException(
                    "Informe login e senha.");
        }

        Usuario usuario =
                this.usuarioDAO.buscarPorLoginESenha(login, senha);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Login ou senha invalidos.");
        }

        return usuario;
    }

    public List<Usuario> listar() {
        return this.usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        return this.usuarioDAO.buscarPorId(id);
    }

    public void salvar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuario e obrigatorio.");
        }

        this.prepararDados(usuario);

        this.validarCamposObrigatorios(usuario);
        this.validarEmail(usuario.getEmail());
        this.validarSenha(usuario.getSenha());
        this.validarPerfilExistente(usuario.getPerfilId());
        this.validarLoginUnico(usuario);
        this.validarEmailUnico(usuario);

        if (usuario.getId() == null) {

            this.usuarioDAO.inserir(usuario);
            return;
        }

        if (this.usuarioDAO.buscarPorId(usuario.getId()) == null) {

            throw new IllegalArgumentException(
                    "Usuario nao encontrado para alteracao.");
        }

        this.usuarioDAO.alterar(usuario);
    }

    public void cadastrarNormal(Usuario usuario) {
        usuario.setId(null);
        usuario.setPerfilId(PERFIL_NORMAL_ID);

        this.salvar(usuario);
    }


    public void deletar(Long id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "Id e obrigatorio para excluir.");
        }

        if (this.usuarioDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "Usuario nao encontrado.");
        }

        this.usuarioDAO.deletar(id);
    }


    private void prepararDados(Usuario usuario) {

        usuario.setNome(
                this.normalizar(usuario.getNome()));

        usuario.setEmail(
                this.normalizar(usuario.getEmail()));

        usuario.setLogin(
                this.normalizar(usuario.getLogin()));

        usuario.setSenha(
                this.normalizar(usuario.getSenha()));
    }


    private void validarCamposObrigatorios(Usuario usuario) {

        if (usuario.getNome() == null) {
            throw new IllegalArgumentException(
                    "Nome e obrigatorio.");
        }

        if (usuario.getEmail() == null) {
            throw new IllegalArgumentException(
                    "Email e obrigatorio.");
        }

        if (usuario.getLogin() == null) {
            throw new IllegalArgumentException(
                    "Login e obrigatorio.");
        }

        if (usuario.getSenha() == null) {
            throw new IllegalArgumentException(
                    "Senha e obrigatoria.");
        }

        if (usuario.getPerfilId() == null) {
            throw new IllegalArgumentException(
                    "Perfil e obrigatorio.");
        }
    }


    private void validarSenha(String senha) {

        if (senha.length() < SENHA_MINIMA) {

            throw new IllegalArgumentException(
                    "Senha deve ter no minimo "
                            + SENHA_MINIMA
                            + " caracteres.");
        }
    }

    private void validarEmail(String email) {

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "Informe um email valido.");
        }
    }

    private void validarPerfilExistente(Long perfilId) {

        if (this.perfilDAO.buscarPorId(perfilId) == null) {

            throw new IllegalArgumentException(
                    "Perfil informado nao existe.");
        }
    }


    private void validarLoginUnico(Usuario usuario) {

        Usuario existente =
                this.usuarioDAO.buscarPorLogin(
                        usuario.getLogin());

        if (existente == null) {
            return;
        }

        if (usuario.getId() == null) {

            throw new IllegalArgumentException(
                    "Ja existe um usuario com este login.");
        }

        if (!existente.getId().equals(usuario.getId())) {

            throw new IllegalArgumentException(
                    "Ja existe um usuario com este login.");
        }
    }

    private void validarEmailUnico(Usuario usuario) {

        Usuario existente =
                this.usuarioDAO.buscarPorEmail(
                        usuario.getEmail());

        if (existente == null) {
            return;
        }

        if (usuario.getId() == null) {

            throw new IllegalArgumentException(
                    "Ja existe um usuario com este email.");
        }

        if (!existente.getId().equals(usuario.getId())) {

            throw new IllegalArgumentException(
                    "Ja existe um usuario com este email.");
        }
    }

    private String normalizar(String valor) {

        if (valor == null) {
            return null;
        }

        String limpo = valor.trim();

        return limpo.isEmpty() ? null : limpo;
    }
}
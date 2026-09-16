package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends MysqlDAO {

    public LivroDAO() {
        super();
    }

    public List<Livro> listarTodos() {

        String sql =
                "SELECT l.id, l.titulo, l.autor, l.usuario_id, l.disponivel, "
                        + "u.nome AS usuario_nome, "
                        + "u.email AS usuario_email "
                        + "FROM livros l "
                        + "INNER JOIN usuarios u ON u.id = l.usuario_id "
                        + "WHERE l.disponivel = TRUE "
                        + "AND u.ativo = TRUE "
                        + "AND NOT EXISTS ("
                        + "SELECT 1 FROM trocas t "
                        + "WHERE t.status IN ('PENDENTE', 'ACEITA') "
                        + "AND (t.livro_oferecido_id = l.id "
                        + "OR t.livro_recebido_id = l.id)) "
                        + "ORDER BY l.titulo";

        List<Livro> lista = new ArrayList<>();

        try (ResultSet rs = super.executar(sql)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar livros.", e);
        }

        return lista;
    }

    public List<Livro> listarDisponiveis(Long usuarioId) {

        String sql =
                "SELECT l.id, l.titulo, l.autor, l.usuario_id, l.disponivel, "
                        + "u.nome AS usuario_nome, "
                        + "u.email AS usuario_email "
                        + "FROM livros l "
                        + "INNER JOIN usuarios u ON u.id = l.usuario_id "
                        + "WHERE l.usuario_id <> ? "
                        + "AND l.disponivel = TRUE "
                        + "AND u.ativo = TRUE "
                        + "AND NOT EXISTS ("
                        + "SELECT 1 FROM trocas t "
                        + "WHERE t.status IN ('PENDENTE', 'ACEITA') "
                        + "AND ("
                        + "t.livro_oferecido_id = l.id "
                        + "OR t.livro_recebido_id = l.id"
                        + ")) "
                        + "ORDER BY l.titulo";

        List<Livro> lista = new ArrayList<>();

        try (ResultSet rs = super.executar(sql, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar livros disponiveis.", e);
        }

        return lista;
    }

    public List<Livro> listarPorUsuario(Long usuarioId) {

        String sql =
                "SELECT l.id, l.titulo, l.autor, l.usuario_id, l.disponivel, "
                        + "u.nome AS usuario_nome, "
                        + "u.email AS usuario_email "
                        + "FROM livros l "
                        + "INNER JOIN usuarios u ON u.id = l.usuario_id "
                        + "WHERE l.usuario_id = ? "
                        + "AND u.ativo = TRUE "
                        + "ORDER BY l.titulo";

        List<Livro> lista = new ArrayList<>();

        try (ResultSet rs = super.executar(sql, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar livros do usuario.", e);
        }

        return lista;
    }

    public List<Livro> listarDisponiveisPorUsuario(Long usuarioId) {

        String sql =
                "SELECT l.id, l.titulo, l.autor, l.usuario_id, l.disponivel, "
                        + "u.nome AS usuario_nome, "
                        + "u.email AS usuario_email "
                        + "FROM livros l "
                        + "INNER JOIN usuarios u ON u.id = l.usuario_id "
                        + "WHERE l.usuario_id = ? "
                        + "AND l.disponivel = TRUE "
                        + "AND u.ativo = TRUE "
                        + "AND NOT EXISTS ("
                        + "SELECT 1 FROM trocas t "
                        + "WHERE t.status IN ('PENDENTE', 'ACEITA') "
                        + "AND ("
                        + "t.livro_oferecido_id = l.id "
                        + "OR t.livro_recebido_id = l.id"
                        + ")) "
                        + "ORDER BY l.titulo";

        List<Livro> lista = new ArrayList<>();

        try (ResultSet rs = super.executar(sql, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar livros disponiveis do usuario.", e);
        }

        return lista;
    }

    public Livro buscarPorId(Long id) {

        String sql =
                "SELECT l.id, l.titulo, l.autor, l.usuario_id, l.disponivel, "
                        + "u.nome AS usuario_nome, "
                        + "u.email AS usuario_email "
                        + "FROM livros l "
                        + "INNER JOIN usuarios u ON u.id = l.usuario_id "
                        + "WHERE l.id = ?";

        try (ResultSet rs = super.executar(sql, id)) {

            if (rs.next()) {
                return this.mapear(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar livro por id.", e);
        }

        return null;
    }

    public void inserir(Livro livro) {

        String sql =
                "INSERT INTO livros "
                + "(titulo, autor, usuario_id, disponivel) "
                + "VALUES (?, ?, ?, FALSE)";

        try {

            super.executarUpdate(
                    sql,
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getUsuarioId());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir livro.", e);
        }
    }

    public void alterar(Livro livro) {

        String sql =
                "UPDATE livros "
                        + "SET titulo = ?, autor = ? "
                        + "WHERE id = ?";

        try {

            super.executarUpdate(
                    sql,
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getId());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao alterar livro.", e);
        }
    }

    public void alterarDisponibilidade(Long id, boolean disponivel) {

        String sql =
                "UPDATE livros SET disponivel = ? WHERE id = ?";

        try {
            super.executarUpdate(sql, disponivel, id);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao alterar disponibilidade do livro.", e);
        }
    }

    public boolean existeTrocaParaLivro(Long livroId) {

        String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM trocas "
                        + "WHERE livro_oferecido_id = ? "
                        + "OR livro_recebido_id = ?";

        try (ResultSet rs =
                     super.executar(sql, livroId, livroId)) {

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao verificar trocas do livro.", e);
        }

        return false;
    }

    public boolean existeTrocaAtivaParaLivro(Long livroId) {

        String sql =
                "SELECT COUNT(*) AS total FROM trocas "
                        + "WHERE status IN ('PENDENTE', 'ACEITA') "
                        + "AND (livro_oferecido_id = ? "
                        + "OR livro_recebido_id = ?)";

        try (ResultSet rs = super.executar(sql, livroId, livroId)) {
            return rs.next() && rs.getInt("total") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao verificar troca ativa do livro.", e);
        }
    }

    public void deletar(Long id) {

        String sql =
                "DELETE FROM livros "
                        + "WHERE id = ?";

        try {

            super.executarUpdate(sql, id);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar livro.", e);
        }
    }

    private Livro mapear(ResultSet rs)
            throws SQLException {

        Livro livro = new Livro();

        livro.setId(rs.getLong("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setUsuarioId(rs.getLong("usuario_id"));
        livro.setDisponivel(rs.getBoolean("disponivel"));

        Usuario usuario = new Usuario();

        usuario.setId(rs.getLong("usuario_id"));
        usuario.setNome(rs.getString("usuario_nome"));
        usuario.setEmail(rs.getString("usuario_email"));

        livro.setUsuario(usuario);

        return livro;
    }
}
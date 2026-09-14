package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.model.Troca;
import br.com.intercambiolivros.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrocaDAO extends MysqlDAO {

    public TrocaDAO() {
        super();
    }

    public void inserir(Troca troca) {

        String sql =
                "INSERT INTO trocas "
                        + "(livro_oferecido_id, livro_recebido_id, "
                        + "status, mensagem) "
                        + "VALUES (?, ?, ?, ?)";

        try {

            super.executarUpdate(
                    sql,
                    troca.getLivroOferecidoId(),
                    troca.getLivroRecebidoId(),
                    troca.getStatus(),
                    troca.getMensagem());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir troca.", e);
        }
    }

    public Troca buscarPorId(Long id) {

        String sql =
                "SELECT "
                        + "t.id AS troca_id, "
                        + "t.livro_oferecido_id, "
                        + "t.livro_recebido_id, "
                        + "t.status, "
                        + "t.mensagem, "
                        + "lo.titulo AS oferecido_titulo, "
                        + "lo.autor AS oferecido_autor, "
                        + "lo.usuario_id AS oferecido_usuario_id, "
                        + "uo.nome AS oferecido_usuario_nome, "
                        + "lr.titulo AS recebido_titulo, "
                        + "lr.autor AS recebido_autor, "
                        + "lr.usuario_id AS recebido_usuario_id, "
                        + "ur.nome AS recebido_usuario_nome "
                        + "FROM trocas t "
                        + "INNER JOIN livros lo "
                        + "ON lo.id = t.livro_oferecido_id "
                        + "INNER JOIN usuarios uo "
                        + "ON uo.id = lo.usuario_id "
                        + "INNER JOIN livros lr "
                        + "ON lr.id = t.livro_recebido_id "
                        + "INNER JOIN usuarios ur "
                        + "ON ur.id = lr.usuario_id "
                        + "WHERE t.id = ?";

        try (ResultSet rs = super.executar(sql, id)) {

            if (rs.next()) {
                return this.mapear(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar troca.", e);
        }

        return null;
    }

    public List<Troca> listarPorUsuario(Long usuarioId) {

        String sql =
                "SELECT "
                        + "t.id AS troca_id, "
                        + "t.livro_oferecido_id, "
                        + "t.livro_recebido_id, "
                        + "t.status, "
                        + "t.mensagem, "
                        + "lo.titulo AS oferecido_titulo, "
                        + "lo.autor AS oferecido_autor, "
                        + "lo.usuario_id AS oferecido_usuario_id, "
                        + "uo.nome AS oferecido_usuario_nome, "
                        + "lr.titulo AS recebido_titulo, "
                        + "lr.autor AS recebido_autor, "
                        + "lr.usuario_id AS recebido_usuario_id, "
                        + "ur.nome AS recebido_usuario_nome "
                        + "FROM trocas t "
                        + "INNER JOIN livros lo "
                        + "ON lo.id = t.livro_oferecido_id "
                        + "INNER JOIN usuarios uo "
                        + "ON uo.id = lo.usuario_id "
                        + "INNER JOIN livros lr "
                        + "ON lr.id = t.livro_recebido_id "
                        + "INNER JOIN usuarios ur "
                        + "ON ur.id = lr.usuario_id "
                        + "WHERE lo.usuario_id = ? "
                        + "OR lr.usuario_id = ? "
                        + "ORDER BY t.id DESC";

        List<Troca> lista = new ArrayList<>();

        try (ResultSet rs =
                     super.executar(sql, usuarioId, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar trocas do usuario.", e);
        }

        return lista;
    }

    public List<Troca> listarPendentesPorUsuario(
            Long usuarioId) {

        String sql =
                "SELECT "
                        + "t.id AS troca_id, "
                        + "t.livro_oferecido_id, "
                        + "t.livro_recebido_id, "
                        + "t.status, "
                        + "t.mensagem, "
                        + "lo.titulo AS oferecido_titulo, "
                        + "lo.autor AS oferecido_autor, "
                        + "lo.usuario_id AS oferecido_usuario_id, "
                        + "uo.nome AS oferecido_usuario_nome, "
                        + "lr.titulo AS recebido_titulo, "
                        + "lr.autor AS recebido_autor, "
                        + "lr.usuario_id AS recebido_usuario_id, "
                        + "ur.nome AS recebido_usuario_nome "
                        + "FROM trocas t "
                        + "INNER JOIN livros lo "
                        + "ON lo.id = t.livro_oferecido_id "
                        + "INNER JOIN usuarios uo "
                        + "ON uo.id = lo.usuario_id "
                        + "INNER JOIN livros lr "
                        + "ON lr.id = t.livro_recebido_id "
                        + "INNER JOIN usuarios ur "
                        + "ON ur.id = lr.usuario_id "
                        + "WHERE t.status = 'PENDENTE' "
                        + "AND lr.usuario_id = ? "
                        + "ORDER BY t.id DESC";

        List<Troca> lista = new ArrayList<>();

        try (ResultSet rs =
                     super.executar(sql, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar trocas pendentes.", e);
        }

        return lista;
    }

    public List<Troca> listarConcluidasPorUsuario(
            Long usuarioId) {

        String sql =
                "SELECT "
                        + "t.id AS troca_id, "
                        + "t.livro_oferecido_id, "
                        + "t.livro_recebido_id, "
                        + "t.status, "
                        + "t.mensagem, "
                        + "lo.titulo AS oferecido_titulo, "
                        + "lo.autor AS oferecido_autor, "
                        + "lo.usuario_id AS oferecido_usuario_id, "
                        + "uo.nome AS oferecido_usuario_nome, "
                        + "lr.titulo AS recebido_titulo, "
                        + "lr.autor AS recebido_autor, "
                        + "lr.usuario_id AS recebido_usuario_id, "
                        + "ur.nome AS recebido_usuario_nome "
                        + "FROM trocas t "
                        + "INNER JOIN livros lo "
                        + "ON lo.id = t.livro_oferecido_id "
                        + "INNER JOIN usuarios uo "
                        + "ON uo.id = lo.usuario_id "
                        + "INNER JOIN livros lr "
                        + "ON lr.id = t.livro_recebido_id "
                        + "INNER JOIN usuarios ur "
                        + "ON ur.id = lr.usuario_id "
                        + "WHERE t.status = 'CONCLUIDA' "
                        + "AND (lo.usuario_id = ? "
                        + "OR lr.usuario_id = ?) "
                        + "ORDER BY t.id DESC";

        List<Troca> lista = new ArrayList<>();

        try (ResultSet rs =
                     super.executar(sql, usuarioId, usuarioId)) {

            while (rs.next()) {
                lista.add(this.mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar trocas concluidas.", e);
        }

        return lista;
    }

    public boolean existeTrocaPendenteParaLivro(
            Long livroId) {

        String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM trocas "
                        + "WHERE status = 'PENDENTE' "
                        + "AND ("
                        + "livro_oferecido_id = ? "
                        + "OR livro_recebido_id = ?"
                        + ")";

        try (ResultSet rs =
                     super.executar(sql, livroId, livroId)) {

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao verificar troca pendente.", e);
        }

        return false;
    }

    public boolean existeTrocaAtivaOuConcluidaParaLivro(
            Long livroId) {

        String sql =
                "SELECT COUNT(*) AS total "
                        + "FROM trocas "
                        + "WHERE status IN "
                        + "('PENDENTE', 'ACEITA', 'CONCLUIDA') "
                        + "AND ("
                        + "livro_oferecido_id = ? "
                        + "OR livro_recebido_id = ?"
                        + ")";

        try (ResultSet rs =
                     super.executar(sql, livroId, livroId)) {

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao verificar disponibilidade do livro.", e);
        }

        return false;
    }

    public void alterarStatus(
            Long id,
            String status) {

        String sql =
                "UPDATE trocas "
                        + "SET status = ? "
                        + "WHERE id = ?";

        try {

            super.executarUpdate(
                    sql,
                    status,
                    id);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao alterar status da troca.", e);
        }
    }

    private Troca mapear(ResultSet rs)
            throws SQLException {

        Troca troca = new Troca();

        troca.setId(
                rs.getLong("troca_id"));

        troca.setLivroOferecidoId(
                rs.getLong("livro_oferecido_id"));

        troca.setLivroRecebidoId(
                rs.getLong("livro_recebido_id"));

        troca.setStatus(
                rs.getString("status"));

        troca.setMensagem(
                rs.getString("mensagem"));

        Livro livroOferecido = new Livro();

        livroOferecido.setId(
                rs.getLong("livro_oferecido_id"));

        livroOferecido.setTitulo(
                rs.getString("oferecido_titulo"));

        livroOferecido.setAutor(
                rs.getString("oferecido_autor"));

        livroOferecido.setUsuarioId(
                rs.getLong("oferecido_usuario_id"));

        Usuario usuarioOferecido =
                new Usuario();

        usuarioOferecido.setId(
                rs.getLong("oferecido_usuario_id"));

        usuarioOferecido.setNome(
                rs.getString("oferecido_usuario_nome"));

        livroOferecido.setUsuario(
                usuarioOferecido);

        troca.setLivroOferecido(
                livroOferecido);

        Livro livroRecebido = new Livro();

        livroRecebido.setId(
                rs.getLong("livro_recebido_id"));

        livroRecebido.setTitulo(
                rs.getString("recebido_titulo"));

        livroRecebido.setAutor(
                rs.getString("recebido_autor"));

        livroRecebido.setUsuarioId(
                rs.getLong("recebido_usuario_id"));

        Usuario usuarioRecebido =
                new Usuario();

        usuarioRecebido.setId(
                rs.getLong("recebido_usuario_id"));

        usuarioRecebido.setNome(
                rs.getString("recebido_usuario_nome"));

        livroRecebido.setUsuario(
                usuarioRecebido);

        troca.setLivroRecebido(
                livroRecebido);

        return troca;
    }
}
package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.TrocaDAO;
import br.com.intercambiolivros.model.Troca;

import java.util.List;

public class TrocaService {

    private final TrocaDAO trocaDAO;

    public TrocaService(String token) {
        this.trocaDAO = new TrocaDAO(token);
    }

    public void solicitar(
            Long usuarioId,
            Long livroOferecidoId,
            Long livroRecebidoId,
            String mensagem) {

        if (usuarioId == null
                || livroOferecidoId == null
                || livroRecebidoId == null) {
            throw new IllegalArgumentException("Selecione os dois livros.");
        }
        if (livroOferecidoId.equals(livroRecebidoId)) {
            throw new IllegalArgumentException("Os livros devem ser diferentes.");
        }
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe uma mensagem ou o local da troca.");
        }

        Troca troca = new Troca();
        troca.setLivroOferecidoId(livroOferecidoId);
        troca.setLivroRecebidoId(livroRecebidoId);
        troca.setMensagem(mensagem.trim());
        this.trocaDAO.inserir(troca);
    }

    public List<Troca> listarPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return this.trocaDAO.listarPorUsuario(usuarioId);
    }

    public List<Troca> listarPendentesPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return this.trocaDAO.listarPendentesPorUsuario(usuarioId);
    }

    public List<Troca> listarConcluidasPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return this.trocaDAO.listarConcluidasPorUsuario(usuarioId);
    }

    public Troca buscarPorId(Long id) {
        return this.trocaDAO.buscarPorId(id);
    }

    public void aceitar(Long trocaId, Long usuarioId) {
        validarAcao(trocaId, usuarioId);
        this.trocaDAO.alterarStatus(trocaId, "ACEITA");
    }

    public void recusar(Long trocaId, Long usuarioId) {
        validarAcao(trocaId, usuarioId);
        this.trocaDAO.alterarStatus(trocaId, "RECUSADA");
    }

    public void concluir(Long trocaId, Long usuarioId) {
        validarAcao(trocaId, usuarioId);
        this.trocaDAO.concluir(trocaId);
    }

    private static void validarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("Usuario nao identificado.");
        }
    }

    private static void validarAcao(Long trocaId, Long usuarioId) {
        if (trocaId == null || usuarioId == null) {
            throw new IllegalArgumentException("Troca e usuario sao obrigatorios.");
        }
    }
}

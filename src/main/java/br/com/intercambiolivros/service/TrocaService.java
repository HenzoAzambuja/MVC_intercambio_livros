package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.TrocaDAO;
import br.com.intercambiolivros.model.Troca;

import java.util.List;

public class TrocaService {

    private final TrocaDAO trocaDAO;

    public TrocaService() {
        this.trocaDAO = new TrocaDAO();
    }

    public void solicitar(
            Long usuarioId,
            Long livroOferecidoId,
            Long livroRecebidoId,
            String mensagem) {

        this.validarUsuario(usuarioId);
        this.validarId(livroOferecidoId);
        this.validarId(livroRecebidoId);

        if (livroOferecidoId.equals(livroRecebidoId)) {
            throw new IllegalArgumentException(
                    "Os livros devem ser diferentes."
            );
        }

        mensagem = this.normalizar(mensagem);

        if (mensagem == null) {
            throw new IllegalArgumentException(
                    "Informe uma mensagem ou o local da troca."
            );
        }

        Troca troca = new Troca();
        troca.setLivroOferecidoId(livroOferecidoId);
        troca.setLivroRecebidoId(livroRecebidoId);
        troca.setMensagem(mensagem);

        this.trocaDAO.inserir(troca);
    }

    public List<Troca> listarPorUsuario(Long usuarioId) {
        this.validarUsuario(usuarioId);
        return this.trocaDAO.listarPorUsuario(usuarioId);
    }

    public List<Troca> listarPendentesPorUsuario(Long usuarioId) {
        this.validarUsuario(usuarioId);
        return this.trocaDAO.listarPendentesPorUsuario(usuarioId);
    }

    public List<Troca> listarConcluidasPorUsuario(Long usuarioId) {
        this.validarUsuario(usuarioId);
        return this.trocaDAO.listarConcluidasPorUsuario(usuarioId);
    }

    public Troca buscarPorId(Long id) {
        if (id == null) {
            return null;
        }

        this.validarId(id);
        return this.trocaDAO.buscarPorId(id);
    }

    public void aceitar(Long trocaId, Long usuarioId) {
        this.validarUsuario(usuarioId);
        this.validarId(trocaId);
        this.trocaDAO.alterarStatus(trocaId, "ACEITA");
    }

    public void recusar(Long trocaId, Long usuarioId) {
        this.validarUsuario(usuarioId);
        this.validarId(trocaId);
        this.trocaDAO.alterarStatus(trocaId, "RECUSADA");
    }

    public void concluir(Long trocaId, Long usuarioId) {
        this.validarUsuario(usuarioId);
        this.validarId(trocaId);
        this.trocaDAO.concluir(trocaId);
    }

    private void validarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new SecurityException(
                    "Usuario nao autenticado."
            );
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Id da troca invalido."
            );
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
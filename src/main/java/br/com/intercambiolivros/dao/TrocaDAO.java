package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiDAO;
import br.com.intercambiolivros.model.Troca;

import java.util.List;

public class TrocaDAO extends ApiDAO {

    public void inserir(Troca troca) {
        apiPost(
                "/api/trocas",
                new SolicitarTrocaRequest(
                        troca.getLivroOferecidoId(),
                        troca.getLivroRecebidoId(),
                        troca.getMensagem()
                )
        );
    }

    public Troca buscarPorId(Long id) {
        try {
            return apiGet("/api/trocas/" + id, Troca.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw e;
        }
    }

    public List<Troca> listarPorUsuario(Long usuarioId) {
        return apiGetLista("/api/trocas", Troca.class);
    }

    public List<Troca> listarPendentesPorUsuario(Long usuarioId) {
        return apiGetLista("/api/trocas/pendentes", Troca.class);
    }

    public List<Troca> listarConcluidasPorUsuario(Long usuarioId) {
        return apiGetLista("/api/trocas/concluidas", Troca.class);
    }

    public void alterarStatus(Long id, String status) {
        String acao;

        if ("ACEITA".equals(status)) {
            acao = "aceitar";
        } else if ("RECUSADA".equals(status)) {
            acao = "recusar";
        } else {
            throw new IllegalArgumentException(
                    "Status de troca não suportado: " + status
            );
        }

        apiPut("/api/trocas/" + id + "/" + acao, null);
    }

    public void concluir(Long id) {
        apiPut("/api/trocas/" + id + "/concluir", null);
    }

    private static class SolicitarTrocaRequest {
        private final Long livroOferecidoId;
        private final Long livroRecebidoId;
        private final String mensagem;

        private SolicitarTrocaRequest(
                Long livroOferecidoId,
                Long livroRecebidoId,
                String mensagem) {
            this.livroOferecidoId = livroOferecidoId;
            this.livroRecebidoId = livroRecebidoId;
            this.mensagem = mensagem;
        }
    }
}
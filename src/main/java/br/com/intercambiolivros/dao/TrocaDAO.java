package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiSingleton;
import br.com.intercambiolivros.model.Troca;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class TrocaDAO {

    private static final Type TROCA_LIST_TYPE =
            TypeToken.getParameterized(List.class, Troca.class).getType();

    private final ApiSingleton api = ApiSingleton.getInstance();
    private final String token;

    public TrocaDAO(String token) {
        this.token = token;
    }

    public void inserir(Troca troca) {
        Map<String, Object> payload = Map.of(
                "livroOferecidoId", troca.getLivroOferecidoId(),
                "livroRecebidoId", troca.getLivroRecebidoId(),
                "mensagem", troca.getMensagem());
        this.api.post("/trocas", this.token, payload, Void.class);
    }

    public Troca buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.api.get("/trocas/" + id, this.token, Troca.class);
    }

    public List<Troca> listarPorUsuario(Long usuarioId) {
        return this.api.get("/trocas", this.token, TROCA_LIST_TYPE);
    }

    public List<Troca> listarPendentesPorUsuario(Long usuarioId) {
        return this.api.get("/trocas/pendentes", this.token, TROCA_LIST_TYPE);
    }

    public List<Troca> listarConcluidasPorUsuario(Long usuarioId) {
        return this.api.get("/trocas/concluidas", this.token, TROCA_LIST_TYPE);
    }

    public void alterarStatus(Long id, String status) {
        String acao;
        if ("ACEITA".equals(status)) {
            acao = "aceitar";
        } else if ("RECUSADA".equals(status)) {
            acao = "recusar";
        } else {
            throw new IllegalArgumentException("Status de troca invalido.");
        }
        this.api.put("/trocas/" + id + "/" + acao, this.token, null, Void.class);
    }

    public void concluir(Long trocaId) {
        this.api.put(
                "/trocas/" + trocaId + "/concluir",
                this.token,
                null,
                Void.class);
    }
}

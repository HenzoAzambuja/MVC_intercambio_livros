package br.com.intercambiolivros.service;

import br.com.intercambiolivros.dao.LivroDAO;
import br.com.intercambiolivros.dao.TrocaDAO;
import br.com.intercambiolivros.model.Livro;
import br.com.intercambiolivros.model.Troca;

import java.util.List;

public class TrocaService {

    private final TrocaDAO trocaDAO;
    private final LivroDAO livroDAO;

    public TrocaService() {
        this.trocaDAO = new TrocaDAO();
        this.livroDAO = new LivroDAO();
    }

    public void solicitar(
            Long usuarioId,
            Long livroOferecidoId,
            Long livroRecebidoId,
            String mensagem) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        if (livroOferecidoId == null
                || livroRecebidoId == null) {

            throw new IllegalArgumentException(
                    "Selecione os dois livros.");
        }

        if (livroOferecidoId.equals(livroRecebidoId)) {
            throw new IllegalArgumentException(
                    "Os livros devem ser diferentes.");
        }

        mensagem = this.normalizar(mensagem);

        if (mensagem == null) {
            throw new IllegalArgumentException(
                    "Informe uma mensagem ou o local da troca.");
        }

        Livro livroOferecido =
                this.livroDAO.buscarPorId(
                        livroOferecidoId);

        Livro livroRecebido =
                this.livroDAO.buscarPorId(
                        livroRecebidoId);

        if (livroOferecido == null
                || livroRecebido == null) {

            throw new IllegalArgumentException(
                    "Livro nao encontrado.");
        }

        if (!livroOferecido.getUsuarioId()
                .equals(usuarioId)) {

            throw new IllegalArgumentException(
                    "O livro oferecido deve pertencer a voce.");
        }

        if (livroRecebido.getUsuarioId()
                .equals(usuarioId)) {

            throw new IllegalArgumentException(
                    "O livro recebido deve pertencer a outra pessoa.");
        }

        if (!livroOferecido.isDisponivel()
                || !livroRecebido.isDisponivel()) {
            throw new IllegalArgumentException(
                    "Os dois livros precisam estar disponiveis para troca.");
        }

        if (this.trocaDAO
                .existeTrocaAtivaOuConcluidaParaLivro(
                        livroOferecidoId)) {

            throw new IllegalArgumentException(
                    "Seu livro ja esta envolvido em uma troca.");
        }

        if (this.trocaDAO
                .existeTrocaAtivaOuConcluidaParaLivro(
                        livroRecebidoId)) {

            throw new IllegalArgumentException(
                    "O livro escolhido ja esta envolvido em uma troca.");
        }

        Troca troca = new Troca();

        troca.setLivroOferecidoId(
                livroOferecidoId);

        troca.setLivroRecebidoId(
                livroRecebidoId);

        troca.setUsuarioOferecedorId(usuarioId);
        troca.setUsuarioRecebedorId(livroRecebido.getUsuarioId());

        troca.setStatus("PENDENTE");

        troca.setMensagem(mensagem);

        this.trocaDAO.inserir(troca);
    }

    public List<Troca> listarPorUsuario(
            Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.trocaDAO.listarPorUsuario(
                usuarioId);
    }

    public List<Troca> listarPendentesPorUsuario(
            Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.trocaDAO
                .listarPendentesPorUsuario(
                        usuarioId);
    }

    public List<Troca> listarConcluidasPorUsuario(
            Long usuarioId) {

        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "Usuario nao identificado.");
        }

        return this.trocaDAO
                .listarConcluidasPorUsuario(
                        usuarioId);
    }

    public Troca buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        return this.trocaDAO.buscarPorId(id);
    }

    public void aceitar(
            Long trocaId,
            Long usuarioId) {

        Troca troca =
                this.buscarPorId(trocaId);

        this.validarTrocaPendente(troca);

        this.validarDonoDoLivroRecebido(
                troca,
                usuarioId);

        this.trocaDAO.alterarStatus(
                trocaId,
                "ACEITA");
    }

    public void recusar(
            Long trocaId,
            Long usuarioId) {

        Troca troca =
                this.buscarPorId(trocaId);

        this.validarTrocaPendente(troca);

        this.validarDonoDoLivroRecebido(
                troca,
                usuarioId);

        this.trocaDAO.alterarStatus(
                trocaId,
                "RECUSADA");
    }

    public void concluir(
            Long trocaId,
            Long usuarioId) {

        Troca troca =
                this.buscarPorId(trocaId);

        if (troca == null) {
            throw new IllegalArgumentException(
                    "Troca nao encontrada.");
        }

        if (!"ACEITA".equals(
                troca.getStatus())) {

            throw new IllegalArgumentException(
                    "Somente trocas aceitas podem ser concluidas.");
        }

        boolean participa =
                troca.getLivroOferecido()
                        .getUsuarioId()
                        .equals(usuarioId)
                ||
                troca.getLivroRecebido()
                        .getUsuarioId()
                        .equals(usuarioId);

        if (!participa) {
            throw new IllegalArgumentException(
                    "Voce nao participa desta troca.");
        }

        Long usuarioOferecedor =
                troca.getLivroOferecido().getUsuarioId();
        Long usuarioRecebedor =
                troca.getLivroRecebido().getUsuarioId();

        this.trocaDAO.concluir(
                troca,
                usuarioOferecedor,
                usuarioRecebedor);
    }

    private void validarTrocaPendente(
            Troca troca) {

        if (troca == null) {
            throw new IllegalArgumentException(
                    "Troca nao encontrada.");
        }

        if (!"PENDENTE".equals(
                troca.getStatus())) {

            throw new IllegalArgumentException(
                    "Esta troca nao esta pendente.");
        }
    }

    private void validarDonoDoLivroRecebido(
            Troca troca,
            Long usuarioId) {

        if (usuarioId == null
                || !troca.getLivroRecebido()
                .getUsuarioId()
                .equals(usuarioId)) {

            throw new IllegalArgumentException(
                    "Você não pode responder esta troca.");
        }
    }

    private String normalizar(String valor) {

        if (valor == null) {
            return null;
        }

        String limpo = valor.trim();

        return limpo.isEmpty()
                ? null
                : limpo;
    }
}
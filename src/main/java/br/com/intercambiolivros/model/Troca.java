package br.com.intercambiolivros.model;

public class Troca {

    private Long id;
    private Long livroOferecidoId;
    private Long livroRecebidoId;
    private String status;
    private String mensagem;

    private Livro livroOferecido;
    private Livro livroRecebido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivroOferecidoId() {
        return livroOferecidoId;
    }

    public void setLivroOferecidoId(Long livroOferecidoId) {
        this.livroOferecidoId = livroOferecidoId;
    }

    public Long getLivroRecebidoId() {
        return livroRecebidoId;
    }

    public void setLivroRecebidoId(Long livroRecebidoId) {
        this.livroRecebidoId = livroRecebidoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Livro getLivroOferecido() {
        return livroOferecido;
    }

    public void setLivroOferecido(Livro livroOferecido) {
        this.livroOferecido = livroOferecido;
    }

    public Livro getLivroRecebido() {
        return livroRecebido;
    }

    public void setLivroRecebido(Livro livroRecebido) {
        this.livroRecebido = livroRecebido;
    }
}
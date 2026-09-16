ALTER TABLE trocas
    ADD COLUMN usuario_oferecedor_id BIGINT NULL,
    ADD COLUMN usuario_recebedor_id BIGINT NULL;

UPDATE trocas t
INNER JOIN livros lo ON lo.id = t.livro_oferecido_id
INNER JOIN livros lr ON lr.id = t.livro_recebido_id
SET t.usuario_oferecedor_id = lo.usuario_id,
    t.usuario_recebedor_id = lr.usuario_id;

ALTER TABLE trocas
    MODIFY usuario_oferecedor_id BIGINT NOT NULL,
    MODIFY usuario_recebedor_id BIGINT NOT NULL;
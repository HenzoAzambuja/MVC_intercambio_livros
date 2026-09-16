ALTER TABLE livros
    ADD COLUMN disponivel BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE livros
SET disponivel = TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM trocas
    WHERE trocas.status IN ('PENDENTE', 'ACEITA', 'CONCLUIDA')
      AND (trocas.livro_oferecido_id = livros.id
           OR trocas.livro_recebido_id = livros.id)
);
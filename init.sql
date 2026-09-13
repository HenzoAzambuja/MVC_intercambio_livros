CREATE DATABASE IF NOT EXISTS mvc_java
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mvc_java;


-- =========================================
-- PERFIS
-- =========================================

CREATE TABLE perfis (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- =========================================
-- USUÁRIOS
-- =========================================

CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT uk_usuario_email
        UNIQUE (email),

    CONSTRAINT uk_usuario_login
        UNIQUE (login),

    CONSTRAINT fk_usuario_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfis(id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- =========================================
-- LIVROS
-- =========================================

CREATE TABLE livros (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    usuario_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_livro_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- =========================================
-- TROCAS
-- =========================================

CREATE TABLE trocas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    livro_oferecido_id BIGINT,
    livro_recebido_id BIGINT,

    PRIMARY KEY (id),

    CONSTRAINT fk_troca_livro_oferecido
        FOREIGN KEY (livro_oferecido_id)
        REFERENCES livros(id),

    CONSTRAINT fk_troca_livro_recebido
        FOREIGN KEY (livro_recebido_id)
        REFERENCES livros(id)
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- =========================================
-- PERFIS
-- =========================================

INSERT INTO perfis (nome)
VALUES
    ('Administrador'),
    ('Usuário');


-- =========================================
-- USUÁRIOS PARA TESTE
-- =========================================

INSERT INTO usuarios (
    nome,
    email,
    login,
    senha,
    perfil_id
)
VALUES
    (
        'Administrador',
        'admin@intercambiolivros.com',
        'admin',
        '123456',
        1
    ),
    (
        'Usuário Teste',
        'usuario@intercambiolivros.com',
        'usuario',
        '123456',
        2
    );


-- =========================================
-- LIVROS PARA TESTE
-- =========================================

INSERT INTO livros (
    titulo,
    autor,
    usuario_id
)
VALUES
    (
        'Dom Casmurro',
        'Machado de Assis',
        2
    ),
    (
        'O Pequeno Príncipe',
        'Antoine de Saint-Exupéry',
        2
    );


-- =========================================
-- VERIFICAÇÃO DOS USUÁRIOS
-- =========================================

SELECT
    u.id,
    u.nome,
    u.email,
    u.login,
    p.nome AS perfil
FROM usuarios u
INNER JOIN perfis p
    ON p.id = u.perfil_id
ORDER BY u.nome;


-- =========================================
-- VERIFICAÇÃO DOS LIVROS
-- =========================================

SELECT
    l.id,
    l.titulo,
    l.autor,
    u.nome AS proprietario
FROM livros l
LEFT JOIN usuarios u
    ON u.id = l.usuario_id
ORDER BY l.titulo;
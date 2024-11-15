CREATE TYPE usuario_tipo AS ENUM ('ADMIN', 'USER');


CREATE TABLE usuario(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    localizacao VARCHAR(255),
    tipo usuario_tipo,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    avaliacao_media int
);


CREATE TABLE Servicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    localizacao VARCHAR(255),
    remoto BOOLEAN DEFAULT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    promovido BOOLEAN DEFAULT FALSE,
);
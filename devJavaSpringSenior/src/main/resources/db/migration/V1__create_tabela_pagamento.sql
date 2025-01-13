-- V1__create_tabela_pagamento.sql

CREATE TABLE IF NOT EXISTS pagamento (
    id SERIAL PRIMARY KEY,                  -- Atributo id com incremento automático
    situacao VARCHAR(255),          -- Atributo situacao como VARCHAR
    descricao VARCHAR(255),                  -- Atributo descricao como VARCHAR
    valor REAL,                   -- Atributo valor como DOUBLE
    dataPagamento DATE,                      -- Atributo dataPagamento como DATE
    dataVencimento DATE                      -- Atributo dataVencimento como DATE
);


CREATE SEQUENCE IF NOT EXISTS pagamento_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 
 -- Criação da tabela usuario
CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

INSERT INTO usuario (email, senha)
VALUES ('dev@linkas.com.br', '$2b$12$04RZq88tq/DybaeAn4RAyOdhqj1EGDqF4/kKM9FORRhGbCEXSHKGW')
ON CONFLICT (email) DO NOTHING;


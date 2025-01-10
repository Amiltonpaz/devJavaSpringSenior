-- V1__create_tabela_pagamento.sql

CREATE TABLE IF NOT EXISTS pagamento (
    id SERIAL PRIMARY KEY,                  -- Atributo id com incremento automático
    situacao VARCHAR(255) NOT NULL,          -- Atributo situacao como VARCHAR
    descricao VARCHAR(255),                  -- Atributo descricao como VARCHAR
    valor REAL NOT NULL,                   -- Atributo valor como DOUBLE
    dataPagamento DATE,                      -- Atributo dataPagamento como DATE
    dataVencimento DATE                      -- Atributo dataVencimento como DATE
);


CREATE SEQUENCE IF NOT EXISTS pagamento_id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
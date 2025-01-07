-- V1__create_tabela_pagamento.sql

CREATE TABLE pagamento (
    id SERIAL PRIMARY KEY,                  -- Atributo id com incremento automático
    situacao VARCHAR(255) NOT NULL,          -- Atributo situacao como VARCHAR
    descricao VARCHAR(255),                  -- Atributo descricao como VARCHAR
    valor REAL NOT NULL,                   -- Atributo valor como DOUBLE
    dataPagamento DATE,                      -- Atributo dataPagamento como DATE
    dataVencimento DATE                      -- Atributo dataVencimento como DATE
);

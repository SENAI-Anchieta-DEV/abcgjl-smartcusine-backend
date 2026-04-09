-- EXTENSÃO PARA UUID
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =========================
-- USUARIOS
-- =========================
CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(120) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          tipo VARCHAR(50) NOT NULL
);

-- =========================
-- FICHAS TECNICAS
-- =========================
CREATE TABLE fichas_tecnicas (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 nome_preparo VARCHAR(255),
                                 tempo_ideal VARCHAR(50),
                                 temperatura_ideal DOUBLE PRECISION
);

-- =========================
-- INSUMOS
-- =========================
CREATE TABLE insumos (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         nome VARCHAR(255),
                         unidade_medida VARCHAR(50),
                         quantidade_estoque DOUBLE PRECISION,
                         data_validade DATE,
                         qr_code VARCHAR(255)
);

-- =========================
-- RELATORIOS
-- =========================
CREATE TABLE relatorios (
                            id_relatorio UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            tipo VARCHAR(100),
                            data VARCHAR(50)
);

-- =========================
-- EQUIPAMENTOS
-- =========================
CREATE TABLE equipamentos (
                              id_equipamento UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              tipo VARCHAR(100),
                              temperatura_atual DOUBLE PRECISION,
                              temperatura_ideal DOUBLE PRECISION,
                              ficha_tecnica_id UUID UNIQUE,
                              CONSTRAINT fk_ficha_tecnica
                                  FOREIGN KEY (ficha_tecnica_id)
                                      REFERENCES fichas_tecnicas(id)
);

-- =========================
-- TEMPORIZADORES
-- =========================
CREATE TABLE temporizadores (
                                id_temporizador UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                tempo_configurado INT,
                                tempo_atual INT,
                                equipamento_id UUID UNIQUE,
                                CONSTRAINT fk_equipamento_temp
                                    FOREIGN KEY (equipamento_id)
                                        REFERENCES equipamentos(id_equipamento)
);

-- =========================
-- ALERTAS
-- =========================
CREATE TABLE alertas (
                         id_alerta UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         tipo VARCHAR(100),
                         mensagem TEXT,
                         equipamento_id UUID,
                         temporizador_id UUID,
                         CONSTRAINT fk_alerta_equipamento
                             FOREIGN KEY (equipamento_id)
                                 REFERENCES equipamentos(id_equipamento),
                         CONSTRAINT fk_alerta_temporizador
                             FOREIGN KEY (temporizador_id)
                                 REFERENCES temporizadores(id_temporizador)
);

-- =========================
-- RELAÇÃO FICHA TECNICA x INSUMO
-- =========================
CREATE TABLE ficha_tecnica_insumos (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                       ficha_tecnica_id UUID NOT NULL,
                                       insumo_id UUID NOT NULL,
                                       quantidade DOUBLE PRECISION,
                                       unidade VARCHAR(50),

                                       CONSTRAINT fk_ficha
                                           FOREIGN KEY (ficha_tecnica_id)
                                               REFERENCES fichas_tecnicas(id),

                                       CONSTRAINT fk_insumo
                                           FOREIGN KEY (insumo_id)
                                               REFERENCES insumos(id)
);
-- ============================================================
-- V1__create_tables_iniciais.sql
-- Plantis - Criação das tabelas iniciais do domínio
-- ============================================================

-- ─── Tipos ENUM ─────────────────────────────────────────────
CREATE TYPE perfil_usuario   AS ENUM ('ADMIN', 'GESTOR', 'OPERADOR');
CREATE TYPE tipo_insumo      AS ENUM ('FERTILIZANTE', 'SEMENTE');
CREATE TYPE status_transporte AS ENUM ('PENDENTE', 'EM_TRANSITO', 'ENTREGUE', 'CANCELADO');

-- ─── USUARIO ────────────────────────────────────────────────
CREATE TABLE usuario (
    id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    nome            VARCHAR(150) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    senha           VARCHAR(255) NOT NULL,
    perfil          perfil_usuario NOT NULL DEFAULT 'OPERADOR',
    ativo           BOOLEAN      NOT NULL DEFAULT TRUE,
    criado_em       TIMESTAMP    NOT NULL DEFAULT now(),
    atualizado_em   TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX idx_usuario_email ON usuario (email);

-- ─── FAZENDA ────────────────────────────────────────────────
CREATE TABLE fazenda (
    id                   UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    nome                 VARCHAR(200)   NOT NULL,
    localizacao          VARCHAR(300)   NOT NULL,
    area_total_hectares  DECIMAL(12,2)  NOT NULL,
    usuario_id           UUID           NOT NULL,
    criado_em            TIMESTAMP      NOT NULL DEFAULT now(),
    atualizado_em        TIMESTAMP      NOT NULL DEFAULT now(),

    CONSTRAINT fk_fazenda_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_fazenda_usuario ON fazenda (usuario_id);

-- ─── INSUMO ─────────────────────────────────────────────────
CREATE TABLE insumo (
    id                   UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    nome                 VARCHAR(200)   NOT NULL,
    tipo                 tipo_insumo    NOT NULL,
    descricao            VARCHAR(500),
    quantidade_stock     DECIMAL(12,2)  NOT NULL DEFAULT 0,
    unidade_medida       VARCHAR(20)    NOT NULL,
    stock_minimo_alerta  DECIMAL(12,2)  NOT NULL DEFAULT 0,
    fazenda_id           UUID           NOT NULL,
    criado_em            TIMESTAMP      NOT NULL DEFAULT now(),
    atualizado_em        TIMESTAMP      NOT NULL DEFAULT now(),

    CONSTRAINT fk_insumo_fazenda
        FOREIGN KEY (fazenda_id) REFERENCES fazenda (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_insumo_fazenda ON insumo (fazenda_id);
CREATE INDEX idx_insumo_tipo    ON insumo (tipo);

-- ─── LOTE_COLHEITA ──────────────────────────────────────────
CREATE TABLE lote_colheita (
    id                 UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_lote        VARCHAR(50)    NOT NULL UNIQUE,
    talhao             VARCHAR(100)   NOT NULL,
    produto            VARCHAR(150)   NOT NULL,
    quantidade_colhida DECIMAL(12,2)  NOT NULL,
    unidade_medida     VARCHAR(20)    NOT NULL,
    data_colheita      DATE           NOT NULL,
    observacoes        TEXT,
    fazenda_id         UUID           NOT NULL,
    criado_em          TIMESTAMP      NOT NULL DEFAULT now(),
    atualizado_em      TIMESTAMP      NOT NULL DEFAULT now(),

    CONSTRAINT fk_lote_colheita_fazenda
        FOREIGN KEY (fazenda_id) REFERENCES fazenda (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_lote_colheita_fazenda ON lote_colheita (fazenda_id);
CREATE INDEX idx_lote_colheita_codigo  ON lote_colheita (codigo_lote);

-- ─── TRANSPORTE ─────────────────────────────────────────────
CREATE TABLE transporte (
    id                UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    transportadora    VARCHAR(200)      NOT NULL,
    placa_veiculo     VARCHAR(20)       NOT NULL,
    motorista         VARCHAR(150)      NOT NULL,
    destino           VARCHAR(300)      NOT NULL,
    data_despacho     DATE              NOT NULL,
    status            status_transporte NOT NULL DEFAULT 'PENDENTE',
    observacoes       TEXT,
    lote_colheita_id  UUID              NOT NULL,
    criado_em         TIMESTAMP         NOT NULL DEFAULT now(),
    atualizado_em     TIMESTAMP         NOT NULL DEFAULT now(),

    CONSTRAINT fk_transporte_lote_colheita
        FOREIGN KEY (lote_colheita_id) REFERENCES lote_colheita (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_transporte_lote     ON transporte (lote_colheita_id);
CREATE INDEX idx_transporte_status   ON transporte (status);

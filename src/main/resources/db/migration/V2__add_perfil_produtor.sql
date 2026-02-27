-- ============================================================
-- V2__add_perfil_produtor.sql
-- Adiciona o perfil PRODUTOR ao enum perfil_usuario
-- ============================================================

ALTER TYPE perfil_usuario ADD VALUE 'PRODUTOR';

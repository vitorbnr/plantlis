package com.vitorbnr.plantlis.exception;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String nomeInsumo) {
        super(String.format("Estoque insuficiente para o insumo '%s'.", nomeInsumo));
    }
}

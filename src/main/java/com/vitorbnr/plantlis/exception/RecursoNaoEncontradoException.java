package com.vitorbnr.plantlis.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(String recurso, Object id) {
        super(String.format("%s não encontrado(a) com ID: %s", recurso, id));
    }
}

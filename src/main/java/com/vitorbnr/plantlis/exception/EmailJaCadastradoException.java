package com.vitorbnr.plantlis.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super(String.format("O e-mail '%s' já está cadastrado no sistema.", email));
    }
}

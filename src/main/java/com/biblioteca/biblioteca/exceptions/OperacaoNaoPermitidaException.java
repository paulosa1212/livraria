package com.biblioteca.biblioteca.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{

    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}

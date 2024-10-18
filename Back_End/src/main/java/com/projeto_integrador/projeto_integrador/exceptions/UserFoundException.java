package com.projeto_integrador.projeto_integrador.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("User already exists");
    }
}

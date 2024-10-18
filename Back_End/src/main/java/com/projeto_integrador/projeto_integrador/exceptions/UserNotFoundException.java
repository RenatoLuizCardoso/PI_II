package com.projeto_integrador.projeto_integrador.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User not found");
    }
}

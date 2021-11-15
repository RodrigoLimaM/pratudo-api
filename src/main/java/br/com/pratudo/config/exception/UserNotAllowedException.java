package br.com.pratudo.config.exception;

public class UserNotAllowedException extends RuntimeException {

    public UserNotAllowedException() {
        super("User not allowed");
    }
}

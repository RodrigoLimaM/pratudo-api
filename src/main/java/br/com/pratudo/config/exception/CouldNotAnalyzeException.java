package br.com.pratudo.config.exception;

public class CouldNotAnalyzeException extends RuntimeException {

    public CouldNotAnalyzeException() {
        super("Could not analyze");
    }
}

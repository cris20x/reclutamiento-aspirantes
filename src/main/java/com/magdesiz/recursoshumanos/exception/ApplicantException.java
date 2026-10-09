package com.magdesiz.recursoshumanos.exception;

public class ApplicantException extends RuntimeException {

    public ApplicantException(String message) {
        super(message);
    }

    public ApplicantException(String message, Throwable cause) {
        super(message, cause);
    }
}

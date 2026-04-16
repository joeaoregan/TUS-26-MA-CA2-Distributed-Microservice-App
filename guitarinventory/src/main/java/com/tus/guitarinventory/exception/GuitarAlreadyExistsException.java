package com.tus.guitarinventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GuitarAlreadyExistsException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public GuitarAlreadyExistsException(String message) {
        super(message);
    }
}
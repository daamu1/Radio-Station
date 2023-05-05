package com.saurabh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProgramNotFoundException extends RuntimeException{
    public ProgramNotFoundException(String message) {
        super(message);
    }
}

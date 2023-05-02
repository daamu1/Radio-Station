package com.saurabh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RadioJockeyNotFoundException extends RuntimeException {

    public RadioJockeyNotFoundException(String message) {
        super(message);
    }
}
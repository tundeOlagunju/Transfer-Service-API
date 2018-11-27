package com.example.transferService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TransactionException extends RuntimeException {

    public TransactionException(String error) {
        super(error);
    }
}

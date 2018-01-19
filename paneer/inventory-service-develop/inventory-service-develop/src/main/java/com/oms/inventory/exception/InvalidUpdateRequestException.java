package com.oms.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUpdateRequestException extends RuntimeException{
    public InvalidUpdateRequestException() {
        super();
    }

    public InvalidUpdateRequestException(String message) {
        super(message);
    }
}

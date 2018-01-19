package com.oms.mail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class OMSMailServerError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OMSMailServerError(String message) {
        super(message);
    }

    public OMSMailServerError(String message, Throwable cause) {
        super(message, cause);
    }
}
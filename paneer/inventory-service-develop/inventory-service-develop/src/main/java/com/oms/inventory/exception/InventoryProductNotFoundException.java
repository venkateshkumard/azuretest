package com.oms.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InventoryProductNotFoundException extends RuntimeException{
    public InventoryProductNotFoundException() {
        super();
    }

    public InventoryProductNotFoundException(String message) {
        super(message);
    }
}

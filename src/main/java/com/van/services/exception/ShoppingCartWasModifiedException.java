package com.van.services.exception;

public class ShoppingCartWasModifiedException extends RuntimeException{
    public ShoppingCartWasModifiedException(String message) {
        super(message);
    }

    public ShoppingCartWasModifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.van.services.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class InvalidTokenException extends AuthenticationException {

    private static final long serialVersionUID = 1L;
    private static final String TOKEN_IS_EMPTY = "Token is empty";

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
       this(TOKEN_IS_EMPTY);
    }

    public InvalidTokenException(String message, Throwable t) {
        super(message, t);
    }
}

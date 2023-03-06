package com.van.services.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserNotLoginException extends AuthenticationException {

    private static final long serialVersionUID = 1L;
    private static final String DEF_MESSAGE = "User is not login !";

    public UserNotLoginException() {
        super(DEF_MESSAGE);
    }

    public UserNotLoginException(String message) {
        super(message);
    }

    public UserNotLoginException(String message, Throwable t) {
        super(message, t);
    }
}

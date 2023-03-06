package com.van.services.security.jwt;

public final class JwtTokenFactory {

    public static TokenValidator createTokenValidator() {
        return new TokenValidatorImp();
    }

    private JwtTokenFactory() {
    }
}

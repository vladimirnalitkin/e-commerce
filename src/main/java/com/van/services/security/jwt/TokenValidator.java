package com.van.services.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import reactor.core.publisher.Mono;

public interface TokenValidator {
    Mono<UsernamePasswordAuthenticationToken> getAuthentication(String authToken);
    boolean validateToken(String token);
}

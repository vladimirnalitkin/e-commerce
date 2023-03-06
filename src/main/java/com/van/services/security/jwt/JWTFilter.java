package com.van.services.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.van.services.constant.AppConstants.AUTHORIZATION_HEADER;
import static com.van.services.constant.AppConstants.AUTHORIZATION_SUF_HEADER;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
@Slf4j
public class JWTFilter implements WebFilter {
    private final TokenValidator tokenValidator;
    public JWTFilter(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.debug("JWTFilter start");
        String jwt = extractTokenFromHeader(exchange.getRequest());
        log.debug("JWTFilter jwt = {}", jwt);
        return tokenValidator.getAuthentication(jwt)
                .onErrorStop()
                .flatMap(authentication -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }

    private String extractTokenFromHeader(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_SUF_HEADER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

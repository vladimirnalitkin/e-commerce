package com.van.services.security.jwt;

import com.van.services.exception.BadRequestException;
import com.van.services.security.SecContextUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import static com.van.services.TestHelper.TEST_ID;
import static com.van.services.TestHelper.TEST_TOKEN;
import static com.van.services.constant.AppConstants.AUTHORIZATION_HEADER;
import static com.van.services.constant.AppConstants.AUTHORIZATION_SUF_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JWTFilterTest {
    private static final String TEST_URL = "/api/test";
    private TokenValidator tokenValidator;
    private JWTFilter jwtFilter;

    @BeforeEach
    public void setup() {
        tokenValidator = new TokenValidatorImp();
        jwtFilter = new JWTFilter(tokenValidator);
    }

    @Test
    void testJWTFilterCorrectToken() {
        MockServerHttpRequest.BaseBuilder request = MockServerHttpRequest.get(TEST_URL)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + TEST_TOKEN);
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        jwtFilter.filter(exchange,
                it -> ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .filter(authentication -> authentication.getPrincipal() instanceof SecContextUser)
                        .map(authentication -> (SecContextUser) authentication.getPrincipal())
                        .doOnSuccess(principal -> assertEquals(TEST_ID, principal.getShoppingCartId()))
                        .then()
        ).block();
    }

    @Test
    void testJWTFilterInvalidToken() {
        String jwt = "wrong_jwt";
        MockServerHttpRequest.BaseBuilder request = MockServerHttpRequest.get(TEST_URL)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_SUF_HEADER + jwt);
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        assertThrows(BadRequestException.class, () -> jwtFilter.filter(exchange, null));
    }

}

package com.van.services.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.services.exception.BadRequestException;
import com.van.services.security.SecContextUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class TokenValidatorImp implements TokenValidator {
    private static final String ROLE_USER = "ROLE_USER";
    private static final Collection<? extends GrantedAuthority> DEF_AUTHORITIES = Set.of(new SimpleGrantedAuthority(ROLE_USER));

    private final ObjectMapper objectMapper;

    /**
     * This is a
     */
    public TokenValidatorImp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Verify and parsing string token.
     *
     * @param authToken string token
     * @return Authentication
     */
    @Override
    public Mono<UsernamePasswordAuthenticationToken> getAuthentication(String authToken) {
        try {
            JWT jwt = objectMapper.readValue(authToken, JWT.class);
            return Mono.just(
                    new UsernamePasswordAuthenticationToken(
                            SecContextUser.builder()
                                    .shoppingCartId(jwt.getSid())
                                    .build()
                            , "JWT", DEF_AUTHORITIES)
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Invalid JWT!");
        }
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            objectMapper.readValue(authToken, JWT.class);
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }

}


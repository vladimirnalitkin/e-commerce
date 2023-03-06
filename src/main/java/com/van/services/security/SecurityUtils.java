package com.van.services.security;

import com.van.services.exception.UserNotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;

/**
 * Utility class for Spring Security.
 */
@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {}

    public static Mono<String> getShoppingCartId() {
        return getCurrentUserDetails().map(SecContextUser::getShoppingCartId).switchIfEmpty(Mono.error(UserNotLoginException::new));
    }

    public static Mono<SecContextUser> getCurrentUserDetails() {
        log.debug("start getCurrentUserDetails()");
        return ReactiveSecurityContextHolder
                .getContext()
                .filter(c -> Objects.nonNull(c.getAuthentication()))
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof SecContextUser)
                .map(authentication -> {
                    SecContextUser user = (SecContextUser) authentication.getPrincipal();
                    log.debug("getCurrentUserDetails() user = {}", user);
                    return user;
                })
            .switchIfEmpty(Mono.error(UserNotLoginException::new));
    }

    public static Mono<Collection<? extends GrantedAuthority>> getCurrentUserAuthority() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).map(Authentication::getAuthorities);
    }
}

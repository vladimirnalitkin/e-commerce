package com.van.services.security.jwt;

import com.van.services.security.SecContextUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithOrgMockUser.Factory.class)
public @interface WithOrgMockUser {

    String[] authorities() default {};
    String shoppingCartId() default "testShoppingCartId";

    class Factory implements WithSecurityContextFactory<WithOrgMockUser> {

        @Override
        public SecurityContext createSecurityContext(WithOrgMockUser annotation) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();

            SecContextUser principal = SecContextUser.builder()
                    .shoppingCartId(annotation.shoppingCartId())
                    .build();

            Authentication auth = new UsernamePasswordAuthenticationToken(principal, annotation.shoppingCartId(), principal.getAuthorities());
            context.setAuthentication(auth);
            return context;
        }
    }
}

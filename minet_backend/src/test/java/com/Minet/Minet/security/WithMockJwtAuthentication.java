package com.Minet.Minet.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockJwtAuthenticationSecurityContextFactory.class)
public @interface WithMockJwtAuthentication {
    long id() default 1L;

    String name() default "lee";

    String email() default "lee@gmail.com";

    String role() default "ROLE_MEMBER";
}

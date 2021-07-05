package com.Minet.Minet.security;

import antlr.Token;
import com.Minet.Minet.domain.member.Email;
import com.Minet.Minet.jwt.JwtAuthentication;
import com.Minet.Minet.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class WithMockJwtAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockJwtAuthentication> {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public SecurityContext createSecurityContext(WithMockJwtAuthentication annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        new JwtAuthentication(annotation.id(), annotation.name(), new Email(annotation.email())),
                        null,
                        createAuthorityList(annotation.role())
                );
        context.setAuthentication(authentication);
        return context;
    }
}

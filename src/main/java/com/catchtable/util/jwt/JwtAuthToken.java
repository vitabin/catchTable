package com.catchtable.util.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthToken extends AbstractAuthenticationToken {
    private final String principal;
    private final String credentials;
    private final Collection<GrantedAuthority> authorities;
    private final boolean authenticated = true;

    public JwtAuthToken(String principal, String credentials, Collection<GrantedAuthority> authorities) {
        super(authorities); // log 출력시 한 번에 보기 위해 설정 null이여도 상관 없음
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }
}

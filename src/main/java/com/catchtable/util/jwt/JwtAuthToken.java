package com.catchtable.util.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthToken extends AbstractAuthenticationToken {
    private final String principal;
    private final String credentials;
    private final Collection<GrantedAuthority> authorities;
    private final boolean authenticated = true;

    public JwtAuthToken(String principal, String credentials, Collection<GrantedAuthority> authorities) {
        super(authorities); // log 출력시 한 번에 보기 위해 설정 null이여도 상관 없음
        principal = principal;
        credentials = credentials;
        authorities = authorities;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
}

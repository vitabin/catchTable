package com.catchtable.filter;

import com.catchtable.response.ErrorResponse;
import com.catchtable.response.error.AuthErrorCode;
import com.catchtable.util.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String author = request.getHeader("Authorization");

        if (author == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = author.substring(7);

        try {
            jwtUtil.getAuthentication(token);
            jwtUtil.validate(token);
        } catch (JwtException je) {
            AuthErrorCode error = AuthErrorCode.INVALID_TOKEN;

            if (je.getClass()
                  .equals(ExpiredJwtException.class)) {
                error = AuthErrorCode.EXPIRED_TOKEN;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(
                        ErrorResponse.of(error)
                    ));
            return;
        }

        Authentication authentication = jwtUtil.getAuthentication(token);
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}


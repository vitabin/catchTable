package com.catchtable.filter;

import com.catchtable.util.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String author = request.getHeader("Authorization");

        if (author == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = author.substring(7);

        try {
            this.jwtUtil.getAuthentication(token);
        } catch (JwtException e) {
            PrintWriter writer = response.getWriter();
            writer.write("Invalid Token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        try {
            this.jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.write("Expired Token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        Authentication authentication = this.jwtUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}


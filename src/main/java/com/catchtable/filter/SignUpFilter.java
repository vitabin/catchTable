package com.catchtable.filter;

import com.catchtable.util.wrapper.CustomRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class SignUpFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (!uri.endsWith("/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String bodyString = new String(bodyBytes, StandardCharsets.UTF_8);

        if (!bodyString.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Map<String, Object> requestBody = objectMapper.readValue(bodyString, Map.class);

            if (requestBody.containsKey("password")) {
                String rawPassword = (String) requestBody.get("password");
                String encryptedPassword = passwordEncoder.encode(rawPassword);
                requestBody.put("password", encryptedPassword);
            }
            bodyString = objectMapper.writeValueAsString(requestBody);
        }

        CustomRequestWrapper wrappedRequest = new CustomRequestWrapper(request, bodyString);
        filterChain.doFilter(wrappedRequest, response);
    }
}

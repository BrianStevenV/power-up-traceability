package com.pragma.traceability.configuration.security;

import com.pragma.traceability.configuration.security.jwt.JwtAuthenticationToken;
import com.pragma.traceability.domain.api.IAuthenticationUserInfoServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter implements IAuthenticationUserInfoServicePort {

    @Value("${jwt.secret}")
    private String secret;

    private List<String> excludedPrefixes = Arrays.asList("/swagger-ui/**", "/actuator/**", "/traceability/logs/");
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String tokenHeader = extractTokenFromHeader(request.getHeader("Authorization"));

            if (tokenHeader != null) {
                String role = extractRoleFromToken(tokenHeader);
                List<String> roleList = Collections.singletonList(role);
                Authentication authentication = new JwtAuthenticationToken(tokenHeader, roleList);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {

            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String extractRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        List<String> roles = (List<String>) claims.get("roles");
        String role = roles.get(0);
        return role;
    }

    @Override
    public Long getIdUserFromToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String tokenHeader = extractTokenFromHeader(request.getHeader("Authorization"));
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(tokenHeader).getBody();
        Long id = claims.get("id", Long.class);
        return id;
    }
}

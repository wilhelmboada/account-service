package com.company.accountservice.controller.security;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        String user = null;

        if(request.getServletPath().matches("/sign-up")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authorizationHeader = request.getHeader("Authorization");
                if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    token = authorizationHeader.substring(7);
                    user = jwtUtil.extractUser(token);
                    request.setAttribute("user", user);
                }

                boolean isValidUser = user != null;
                boolean isNullAuthentication = SecurityContextHolder.getContext().getAuthentication() == null;
                boolean isAValidToken = Boolean.TRUE.equals((jwtUtil.validateToken(token)));
                if (isValidUser && isNullAuthentication && isAValidToken) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
            } catch (SignatureException | MalformedJwtException e) {
                log.error("Spring Security Filter Chain Exception:", e);
                resolver.resolveException(request, response, null, e);
            }
        }
    }
}

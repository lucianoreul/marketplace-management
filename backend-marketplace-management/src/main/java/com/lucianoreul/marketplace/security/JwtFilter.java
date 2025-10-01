package com.lucianoreul.marketplace.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String email = jwtUtil.extractUsername(token);

                if (email != null && jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (ExpiredJwtException e) {
                sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "Token expirado", "O token fornecido está expirado");
                return;

            } catch (MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
                sendJsonError(response, HttpServletResponse.SC_FORBIDDEN,
                        "Token inválido", "O token fornecido é inválido");
                return;

            } catch (Exception e) {
                sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Erro interno do servidor", "Erro ao validar token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendJsonError(HttpServletResponse response, int status, String error, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\":\"%s\",\"message\":\"%s\"}", error, message));
    }


}

package br.com.clinicaqr.infrastructure.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwt;
    public JwtAuthenticationFilter(JwtService jwt) { this.jwt=jwt; }
    @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
        String header=req.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")) {
            try {
                UUID id=jwt.userId(header.substring(7));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id,null,List.of()));
            } catch(Exception ignored) { SecurityContextHolder.clearContext(); }
        }
        chain.doFilter(req,res);
    }
}
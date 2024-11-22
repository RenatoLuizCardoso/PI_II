package com.projeto_integrador.projeto_integrador.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projeto_integrador.projeto_integrador.providers.JWTStudentProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityStudentFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTStudentProvider jwtProvider;

    private static final String[] AUTHORIZE = {
      "/student/",
      "/reservation/"
  };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      // SecurityContextHolder.getContext().setAuthentication(null);
      String header = request.getHeader("Authorization");

      boolean isAuthorize = Arrays.stream(AUTHORIZE)
                                  .anyMatch(path -> request.getRequestURI().startsWith(path));
  
      if (isAuthorize) {
        if (header != null) {
          var token = this.jwtProvider.validateToken(header);
  
          if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
          }
  
          request.setAttribute("student_id", token.getSubject());
          var roles = token.getClaim("roles").asList(Object.class);
  
          var grants = roles.stream()
              .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
              .toList();
  
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
              grants);
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
  
      }
  
      filterChain.doFilter(request, response);
    }
}

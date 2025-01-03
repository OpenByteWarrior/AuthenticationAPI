package com.authentication_api.infrastructure.security;

import com.authentication_api.application.dto.response.ResponseHttpDTO;
import com.authentication_api.application.service.JwtService;
import com.authentication_api.application.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Locale locale = request.getLocale();
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/auth/login") || requestURI.equals("/api/auth/register") || requestURI.equals("/api/welcome")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpStatus.FORBIDDEN, messageSource.getMessage("info.token.required", null, locale));
            return;
        }
        String token = getTokenFromRequest(request);

        if (token != null && jwtService.validateToken(token)) {
            String username = jwtService.getUsernameFromToken(token);
            UUID roleId = UUID.fromString(jwtService.getRoleIdFromToken(token));

            String roleName = roleService.getRoleNameById(roleId);
            String prefixedRole = "ROLE_" + roleName;

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            List.of(new SimpleGrantedAuthority(prefixedRole)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        try {
            if (!jwtService.validateToken(token)) {
                throw new RuntimeException(messageSource.getMessage("info.token.invalid", null, locale));
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            sendErrorResponse(response, HttpStatus.FORBIDDEN, ex.getMessage());
        }

    }
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ResponseHttpDTO errorResponse = new ResponseHttpDTO(status, message);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}

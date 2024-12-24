package com.authentication_api.config;

import com.authentication_api.infrastructure.security.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationJwt {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/auth/**", "/api/welcome").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

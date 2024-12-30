package com.authentication_api.application.usecase;

import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.application.dto.request.RequestLoginDTO;
import com.authentication_api.application.service.JwtService;
import com.authentication_api.application.service.UserService;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final UserService userService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;
    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;


    public UserDTO register(UserDTO user,Locale locale) {
        return modelMapper.map(userService.createUser(user,locale), UserDTO.class);
    }
    public String login(RequestLoginDTO userLoginDTO, Locale locale) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
                );
                CustomUserDetails user = userGateway.findByEmail(userLoginDTO.getEmail()).orElseThrow();
                return jwtService.generateToken(user);
            } catch (Exception e) {
                throw new BadCredentialsException(messageSource.getMessage("info.auth.login.failed",new Object[]{e},locale));
            }
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public void logout() {
        // Logout user
    }
}

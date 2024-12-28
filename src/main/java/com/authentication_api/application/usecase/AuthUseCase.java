package com.authentication_api.application.usecase;

import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.application.dto.request.RequestLoginDTO;
import com.authentication_api.application.service.JwtService;
import com.authentication_api.application.service.UserService;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final UserService userService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserGateway userGateway;
    private final AuthenticationManager authenticationManager;


    public UserDTO register(UserDTO user) {
        return modelMapper.map(userService.createUser(user), UserDTO.class);
    }
    public String login(RequestLoginDTO userLoginDTO) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
                );
                CustomUserDetails user = userGateway.findByEmail(userLoginDTO.getEmail()).orElseThrow();
                return jwtService.generateToken(user);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid email or password", e);
            }
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public void logout() {
        // Logout user
    }
}

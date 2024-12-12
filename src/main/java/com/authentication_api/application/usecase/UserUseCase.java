package com.authentication_api.application.usecase;

import com.authentication_api.application.dto.RequestChangePasswordDTO;
import com.authentication_api.application.service.UserService;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.persistence.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService userService;
    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(UUID id, RequestChangePasswordDTO requestChangePasswordDTO) {
        User user = userService.getUserById(id);
        if (!passwordEncoder.matches(requestChangePasswordDTO.getCurrentpassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }
        user.setPassword(passwordEncoder.encode(requestChangePasswordDTO.getNewpassword()));
        userGateway.save(user);
    }
    public void asignRole() {
    }
    public void removeRole() {
    }
}

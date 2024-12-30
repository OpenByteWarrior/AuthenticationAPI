package com.authentication_api.application.usecase;

import com.authentication_api.application.dto.request.RequestChangePasswordDTO;
import com.authentication_api.application.service.UserService;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserService userService;
    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    public void changePassword(UUID id, RequestChangePasswordDTO requestChangePasswordDTO, Locale locale) {
        User user = userService.getUserById(id,locale);
        if (!passwordEncoder.matches(requestChangePasswordDTO.getCurrentpassword(), user.getPassword())) {
            throw new IllegalArgumentException(messageSource.getMessage("info.user.password.update.not.match", null, locale));
        }
        user.setPassword(passwordEncoder.encode(requestChangePasswordDTO.getNewpassword()));
        userGateway.save(user);
    }
    public void asignRole() {
    }
    public void removeRole() {
    }
}

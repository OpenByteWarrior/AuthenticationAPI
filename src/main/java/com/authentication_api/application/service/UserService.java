package com.authentication_api.application.service;

import com.authentication_api.application.dto.request.RequestUserUpdateDTO;
import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.persistence.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserGateway userGateway;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    public User createUser(UserDTO userDTO, Locale locale) {
        if (userGateway.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException(messageSource.getMessage("info.user.email.exists", null, locale));
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);
        return (userGateway.save(user));
    }

    public User getUserById(UUID id, Locale locale) {
        return userGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("info.user.not.found", null, locale)));
    }

    public List<UserDTO> getAllUsers() {
        return userGateway.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User updateUserById(UUID id, RequestUserUpdateDTO requestUser, Locale locale) {

        User user = userGateway.findById(id).orElseThrow(()
                -> new EntityNotFoundException(messageSource.getMessage("info.user.not.found", null, locale)));

        modelMapper.map(requestUser, user);
        return userGateway.save(user);
    }

    public void deleteUserById(UUID id, Locale locale) {
        userGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("info.user.not.found", null, locale)));
        userGateway.deleteById(id);
    }
}

package com.authentication_api.application.service;

import com.authentication_api.application.dto.RequestUserUpdateDTO;
import com.authentication_api.application.dto.UserDTO;
import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.persistence.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserGateway userGateway;
    private final ModelMapper modelMapper;

    public User createUser(UserDTO userDTO) {
        if (userGateway.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un usuario con ese email ya existe");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);
        return (userGateway.save(user));
    }

    public User getUserById(UUID id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public List<UserDTO> getAllUsers() {
        return userGateway.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User updateUserById(UUID id, RequestUserUpdateDTO requestUser) {

        User user = userGateway.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Usuario no encontrado"));

        modelMapper.map(requestUser, user);
        return userGateway.save(user);
    }

    public void deleteUserById(UUID id) {
        userGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        userGateway.deleteById(id);
    }
}

package com.authentication_api.domain;

import com.authentication_api.infrastructure.persistence.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    User save(User Users);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    void deleteById(UUID id);
}

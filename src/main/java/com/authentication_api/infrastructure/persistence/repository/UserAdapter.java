package com.authentication_api.infrastructure.persistence.repository;

import com.authentication_api.domain.UserGateway;
import com.authentication_api.infrastructure.persistence.entity.User
        ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public User save(User users) {
        return this.userRepository.save(users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {this.userRepository.deleteById(id);}

    @Override
    public List<User> findAll() {
        return  this.userRepository.findAll();
    }
}

package com.authentication_api.infrastructure.rest;

import com.authentication_api.application.dto.ResponseHttpDTO;
import com.authentication_api.application.dto.UserDTO;
import com.authentication_api.application.dto.UserLoginDTO;
import com.authentication_api.application.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<ResponseHttpDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authUseCase.login(userLoginDTO);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Login successful", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseHttpDTO> logout() {
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Logout successful",null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHttpDTO> register(@RequestBody UserDTO userDTO
    ) {
        UserDTO createdUser = authUseCase.register(userDTO);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.CREATED, "User registered successfully", createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

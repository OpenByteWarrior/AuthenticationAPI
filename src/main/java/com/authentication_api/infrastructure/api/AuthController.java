package com.authentication_api.infrastructure.api;

import com.authentication_api.application.dto.response.ResponseHttpDTO;
import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.application.dto.request.RequestLoginDTO;
import com.authentication_api.application.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ResponseHttpDTO> login(@RequestBody RequestLoginDTO userLoginDTO) {
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/validate")
    public ResponseEntity<ResponseHttpDTO> validateToken(@RequestBody String token) {
        boolean isValid = authUseCase.validateToken(token);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Token is valid", isValid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

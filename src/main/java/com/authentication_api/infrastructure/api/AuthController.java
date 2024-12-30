package com.authentication_api.infrastructure.api;

import com.authentication_api.application.dto.request.RequestValidateToken;
import com.authentication_api.application.dto.response.ResponseHttpDTO;
import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.application.dto.request.RequestLoginDTO;
import com.authentication_api.application.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;
    private final MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity<ResponseHttpDTO> login(@RequestBody RequestLoginDTO userLoginDTO, Locale locale) {
        String token = authUseCase.login(userLoginDTO, locale);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK,messageSource.getMessage("info.auth.login.success",null,locale), token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseHttpDTO> logout(Locale locale) {
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.auth.logout.success",null,locale),null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHttpDTO> register(@RequestBody UserDTO userDTO,Locale locale) {
        UserDTO createdUser = authUseCase.register(userDTO, locale);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.CREATED, messageSource.getMessage("info.auth.register.success",null,locale), createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/validate")
    public ResponseEntity<ResponseHttpDTO> validateToken(@RequestBody RequestValidateToken body,Locale locale) {
        boolean isValid = authUseCase.validateToken(body.getToken());
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.auth.token.valid",null,locale), isValid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package com.authentication_api.infrastructure.exception;

import com.authentication_api.application.dto.ResponseHttpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseHttpDTO> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseHttpDTO> handleRuntimeException(RuntimeException ex) {
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseHttpDTO> handleGeneralException(Exception ex) {
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


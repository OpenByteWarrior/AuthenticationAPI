package com.authentication_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHttpDTO {
    private HttpStatus status;
    private String message;
    private Object data;

    public ResponseHttpDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}

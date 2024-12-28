package com.authentication_api.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RequestUserUpdateDTO {
    private String firstName;
    private String lastName;
    private UUID roleId;
}

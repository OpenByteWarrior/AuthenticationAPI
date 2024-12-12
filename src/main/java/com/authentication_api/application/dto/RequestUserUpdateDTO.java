package com.authentication_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RequestUserUpdateDTO {
    String firstName;
    String lastName;
    UUID roleId;
}

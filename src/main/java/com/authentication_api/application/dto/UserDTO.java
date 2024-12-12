package com.authentication_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    UUID id;
    String email;
    String nickname;
    String password;
    String firstName;
    String lastName;
    UUID roleId;
}

package com.authentication_api.infrastructure.rest;

import com.authentication_api.application.dto.*;
import com.authentication_api.application.service.UserService;
import com.authentication_api.application.usecase.UserUseCase;
import com.authentication_api.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserUseCase userUseCase;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseHttpDTO> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Usuarios obtenidos correctamente",users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> getUserById(@PathVariable("id") UUID id) {
        UserDTO user = modelMapper.map(userService.getUserById(id), UserDTO.class);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Usuario obtenido correctamente", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{id}")
    public ResponseEntity<ResponseHttpDTO> changePassword(@PathVariable("id") UUID id,@RequestBody RequestChangePasswordDTO requestChangePasswordDTO) {
        userUseCase.changePassword(id,requestChangePasswordDTO);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Contraseña cambiada correctamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> updateUserById(@PathVariable("id") UUID id, @RequestBody RequestUserUpdateDTO requestUserUpdateDTO) {
        UserDTO user = modelMapper.map(userService.updateUserById(id, requestUserUpdateDTO), UserDTO.class);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Usuario actualizado correctamente ",user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteUserById(id);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, "Usuario eliminado correctamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.authentication_api.infrastructure.api;

import com.authentication_api.application.dto.common.UserDTO;
import com.authentication_api.application.dto.request.RequestChangePasswordDTO;
import com.authentication_api.application.dto.request.RequestUserUpdateDTO;
import com.authentication_api.application.dto.response.ResponseHttpDTO;
import com.authentication_api.application.service.JwtService;
import com.authentication_api.application.service.UserService;
import com.authentication_api.application.usecase.UserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserUseCase userUseCase;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseHttpDTO> getAllUsers(Locale locale) {
        List<UserDTO> users = userService.getAllUsers();
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.user.all.success",null,locale),users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> getUserById(@PathVariable("id") UUID id, HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        if (jwtService.checkAccessToUser(id, token)) {
            throw new AccessDeniedException(messageSource.getMessage("info.user.get.unauthorized",null,request.getLocale()));
        }
        UserDTO user = modelMapper.map(userService.getUserById(id,request.getLocale()), UserDTO.class);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.user.get.success",null,request.getLocale()), user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{id}")
    public ResponseEntity<ResponseHttpDTO> changePassword(@PathVariable("id") UUID id,@RequestBody RequestChangePasswordDTO requestChangePasswordDTO, HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        if (jwtService.checkAccessToUser(id, token)) {
            throw new AccessDeniedException(messageSource.getMessage("info.user.password.update.unauthorized",null,request.getLocale()));
        }
        userUseCase.changePassword(id,requestChangePasswordDTO,request.getLocale());
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.user.password.update.success",null,request.getLocale()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> updateUserById(@PathVariable("id") UUID id, @RequestBody RequestUserUpdateDTO requestUserUpdateDTO, HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        if (jwtService.checkAccessToUser(id, token)) {
            throw new AccessDeniedException(messageSource.getMessage("info.user.update.unauthorized",null,request.getLocale()));
        }
        UserDTO user = modelMapper.map(userService.updateUserById(id, requestUserUpdateDTO,request.getLocale()), UserDTO.class);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.user.update.success",null,request.getLocale()),user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseHttpDTO> deleteUserById(@PathVariable("id") UUID id,Locale locale) {
        userService.deleteUserById(id,locale);
        ResponseHttpDTO response = new ResponseHttpDTO(HttpStatus.OK, messageSource.getMessage("info.user.delete.success",null,locale));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

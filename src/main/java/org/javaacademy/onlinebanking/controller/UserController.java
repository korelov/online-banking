package org.javaacademy.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.SingInDto;
import org.javaacademy.onlinebanking.dto.UserDto;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        String pin = userService.registerUser(userDto.getPhone(), userDto.getFullName());
        return ResponseEntity.status(HttpStatus.CREATED).body(pin);
    }

    @PostMapping("/user/auth")
    public ResponseEntity<?> signIn(@RequestBody SingInDto singInDto) {
        String token = userService.authenticateUser(singInDto.getPhone(), singInDto.getPin());
        return ResponseEntity.accepted().body(token);
    }

    @GetMapping()
    public User getUserByToken(@RequestHeader String token) {
        return userService.getUserByToken(token);
    }
}

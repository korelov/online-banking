package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.SingInDto;
import org.javaacademy.onlinebanking.dto.UserDto;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank/user")
@Tag(name = "Контроллер для работы с пользователями", description = "Содержит в себе методы " +
        "регистрации и аутентификации пользователя")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Создаст пользователя. Вернет пинкод."
    )
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        String pin = userService.registerUser(userDto.getPhone(), userDto.getFullName());
        return ResponseEntity.status(HttpStatus.CREATED).body(pin);
    }

    @PostMapping("/auth")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Проверяет подлинность предъявленного пользователем идентификатора." +
                    " Вернет токен"
    )
    public ResponseEntity<?> signIn(@RequestBody SingInDto singInDto) {
        String token = userService.authenticateUser(singInDto.getPhone(), singInDto.getPin());
        return ResponseEntity.accepted().body(token);
    }
}

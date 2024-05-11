package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.PinDto;
import org.javaacademy.onlinebanking.dto.SignInDto;
import org.javaacademy.onlinebanking.dto.TokenDto;
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
@RequestMapping("/api/bank")
@Tag(name = "Контроллер для работы с пользователями", description = "Содержит в себе методы " +
        "регистрации и аутентификации пользователя")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Создаст пользователя. Вернет PIN-код."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь создан", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PinDto.class)))
            })
    })
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        PinDto pin = userService.registerUser(userDto.getPhone(), userDto.getFullName());
        return ResponseEntity.status(HttpStatus.CREATED).body(pin);
    }

    @PostMapping("/user/auth")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Проверяет подлинность предъявленного пользователем идентификатора." +
                    " Вернет токен"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Аутентификация прошла успешно",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TokenDto.class)))
                    })
    })
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
        TokenDto token = userService.authenticateUser(signInDto.getPhone(), signInDto.getPin());
        return ResponseEntity.accepted().body(token);
    }
}

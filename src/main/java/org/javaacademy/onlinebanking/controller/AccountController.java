package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.TokenDto;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.service.AccountService;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
@Tag(name = "Контроллер по счету", description = "Создание счета, Проверка баланса, Все счета пользователя по ТОКЕНУ")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/account")
    @Operation(
            summary = "Список всех активных счетов пользователя ПО ТОКЕНУ",
            description = "Принимает в параметре запроса ТОКЕН и возвращает список всех счетов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Успешно", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Account.class)))
            })
    })
    public ResponseEntity<List<Account>> gelAllUserAccountByToken(
            @RequestParam @Parameter(description = "Уникальный токен пользователя") String token) {
        User userByToken = userService.getUserByToken(token);
        List<Account> userAccounts = accountService.getUserAccounts(userByToken);
        return ResponseEntity.accepted().body(userAccounts);
    }

    @GetMapping("/account/{accountId}")
    @Operation(
            summary = "Проверка баланса счета",
            description = "Вернет остаток по номеру счета"
    )
    public ResponseEntity<?> getBalance(
            @PathVariable @Parameter(description = "Номер счета пользователя") String accountId) {
        BigDecimal accountBalance = accountService.getAccountBalance(accountId);
        return ResponseEntity.status(ACCEPTED).body(accountBalance);
    }

    @PostMapping("/account")
    @Operation(
            summary = "Создает счет для пользователя ПО ТОКЕНУ",
            description = "В теле запроса принимаем токен пользователя и создает счет"
                    + " и возвращает уникальный номер счета"
    )
    public ResponseEntity<?> createAccount(@RequestBody TokenDto tokenDto) {
        User userByToken = userService.getUserByToken(tokenDto.getToken());
        String accountId = accountService.createAccountForUser(userByToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountId);
    }
}

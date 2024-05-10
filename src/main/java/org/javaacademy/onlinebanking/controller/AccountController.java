package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.AccountDto;
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
@Tag(name="Контроллер финансовых операций", description="Описание контролера")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    /**
     * Сделать endpoint GET /account?token=[токен пользователя]
     * - вернет списоск счетов у пользователя.
     *
     * @param token
     * @return
     */
    @GetMapping("/account")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<List<Account>> gelAllUserAccountByToken(@RequestParam String token) {
        User userByToken = userService.getUserByToken(token);
        List<Account> userAccounts = accountService.getUserAccounts(userByToken);
        return ResponseEntity.accepted().body(userAccounts);
    }

    /**
     * Сделать endpoint GET /account/{номер счета} - вернет остаток по номеру счета.
     *
     * @param accountId
     * @return
     */
    @GetMapping("/account/{accountId}")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<?> getBalance(@PathVariable String accountId) {
        BigDecimal accountBalance = accountService.getAccountBalance(accountId);
        return ResponseEntity.status(ACCEPTED).body(accountBalance);
    }

    @PostMapping("/account")
    @Operation(
            summary = "",
            description = ""
    )
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto) {
        User userByToken = userService.getUserByToken(accountDto.getToken());
        String accountId = accountService.createAccountForUser(userByToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountId);
    }
}

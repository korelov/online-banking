package org.javaacademy.onlinebanking.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.PayDto;
import org.javaacademy.onlinebanking.dto.ReceiveDto;
import org.javaacademy.onlinebanking.entity.FinancialOperation;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
@Tag(name = "Контроллер финансовых операций", description = "Можно посмотреть все финансовые операции " +
        "пользователя, сделать платеж(списание) или пополнить счет")
public class FinancialOperationController {
    private final BankService bankService;

    @GetMapping("/operations")
    @Operation(
            summary = "Список всех операций пользователя ПО ТОКЕНУ",
            description = "Принимает в параметре запроса ТОКЕН и возвращает список всех операций," +
                    " отсортированный по дате"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Успешно", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FinancialOperation.class)))
            })
    })
    public ResponseEntity<List<FinancialOperation>> getAllOperationByUser(@RequestParam String token) {
        List<FinancialOperation> paymentHistory = bankService.getPaymentHistory(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentHistory);
    }

    @PostMapping("/operations/pay")
    @Operation(
            summary = "Совершить платеж(Списание) по счету",
            description = "Принимает тело запроса - токен, сумму операции, номер счета, описание операции." +
                    " Делает платеж по пользовательскому счету"
    )
    public ResponseEntity<?> makePayment(@RequestBody PayDto payDto) {
        bankService.makePayment(payDto.getAccountId(),
                payDto.getAmount(),
                payDto.getDescription(),
                payDto.getToken());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/operations/receive")
    @Operation(
            summary = "Пополнение счета",
            description = "В тело запроса положить сумму операции, номер счета, описание операции. " +
                    "Делает зачисление по этому счету"
    )
    public ResponseEntity<?> makeDeposit(@RequestBody ReceiveDto receiveDto) {
        bankService.makeDeposit(receiveDto.getAccountId(),
                receiveDto.getAmount(),
                receiveDto.getDescription());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Межбанковский перевод",
            description = "Принимает тело запроса - токен, сумму операции, номер счета, описание операции." +
                    " Делает межбанковский перевод на счет банка партнера"
    )
    public ResponseEntity<?> transferToAnotherBank(@RequestBody PayDto payDto) {
        bankService.transferToAnotherBank(payDto.getToken(),
                payDto.getAmount(),
                payDto.getDescription(),
                payDto.getAccountId());
        return ResponseEntity.accepted().build();
    }
}

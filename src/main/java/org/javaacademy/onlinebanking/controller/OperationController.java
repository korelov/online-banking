package org.javaacademy.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.PayDto;
import org.javaacademy.onlinebanking.dto.ReceiveDto;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank")
public class OperationController {
    private final BankService bankService;

    /**
     * Сделать endpoint GET /operations?token=[токен пользователя]
     * - вернет список операций для пользователя.
     *
     * @param token
     * @return
     */
    @GetMapping("/operations")
    public ResponseEntity<List<Operation>> getAllOperationByUser(@RequestParam String token) {
        List<Operation> paymentHistory = bankService.getPaymentHistory(token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentHistory);
    }

    /**
     * Сделать endpoint POST /operations/pay. В тело запроса положить токен,
     * сумму операции, номер счета, описание операции.
     * Делает платеж по пользовательскому счету.
     *
     * @param payDto
     * @return
     */
    @PostMapping("/operations/pay")
    public ResponseEntity<?> makePayment(@RequestBody PayDto payDto) {
        bankService.makePayment(payDto.getAccountId(),
                payDto.getAmount(),
                payDto.getDescription(),
                payDto.getToken());
        return ResponseEntity.accepted().build();
    }

    /**
     * Сделать endpoint POST /operations/receive. В тело запроса положить сумму операции,
     * номер счета, описание операции. Делает зачисление по этому счету.
     *
     * @param receiveDto
     * @return
     */
    @PostMapping("/operations/receive")
    public ResponseEntity<?> makeDeposit(@RequestBody ReceiveDto receiveDto) {
        bankService.makeDeposit(receiveDto.getAccountId(),
                receiveDto.getAmount(),
                receiveDto.getDescription());
        return ResponseEntity.accepted().build();
    }
}

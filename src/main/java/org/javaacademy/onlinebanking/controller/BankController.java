package org.javaacademy.onlinebanking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankConfiguration;
import org.javaacademy.onlinebanking.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Контроллер банка", description = "Содержит в себе метод для получения информации о банке")
public class BankController {
    private final BankConfiguration bankConfiguration;
    private final BankService bankService;

    @GetMapping("/bank-info")
    @Operation(summary = "Получаем название банка")
    public ResponseEntity<?> getBankInfo() {
        return ResponseEntity.ok(bankConfiguration.getBankName());
    }
}

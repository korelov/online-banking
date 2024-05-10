package org.javaacademy.onlinebanking.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BankController {
    private final BankConfiguration bankConfiguration;

    @GetMapping("/bank-info")
    public ResponseEntity<?> getBankInfo() {
        return ResponseEntity.ok(bankConfiguration.getBankName());
    }

    @PostMapping("/tramsfer")
    public ResponseEntity<?> transferToAnotherBank(){


        return null;
    }


}

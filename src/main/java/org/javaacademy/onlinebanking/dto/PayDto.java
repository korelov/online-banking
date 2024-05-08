package org.javaacademy.onlinebanking.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PayDto {
    String token;
    BigDecimal amount;
    String accountId;
    String description;
}

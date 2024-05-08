package org.javaacademy.onlinebanking.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ReceiveDto {
    BigDecimal amount;
    String accountId;
    String description;
}

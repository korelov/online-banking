package org.javaacademy.onlinebanking.entity;

import lombok.Data;
import org.javaacademy.onlinebanking.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Operation {
    private String id;
    private LocalDateTime localDateTime = LocalDateTime.now();
    private String accountId;
    private OperationType operationType;
    private BigDecimal amount;
    private String description;
}

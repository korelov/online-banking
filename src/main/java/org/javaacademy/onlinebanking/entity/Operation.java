package org.javaacademy.onlinebanking.entity;

import lombok.Data;
import lombok.NonNull;
import org.javaacademy.onlinebanking.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Operation {
    @NonNull
    private UUID operationId;
    private LocalDateTime localDateTime = LocalDateTime.now();
    @NonNull
    private String accountId;
    @NonNull
    private OperationType operationType;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String description;
}

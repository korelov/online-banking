package org.javaacademy.onlinebanking.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FinancialOperationType {
    WITHDRAW("Списание"),
    DEPOSIT("Зачисление"),
    TRANSFER("Перевод");

    private final String operation;
}

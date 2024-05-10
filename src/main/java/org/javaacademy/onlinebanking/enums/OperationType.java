package org.javaacademy.onlinebanking.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperationType {
    WITHDRAW("Списание"),
    DEPOSIT("Зачисление"),
    TRANSFER("Перевод");

    private final String operation;
}

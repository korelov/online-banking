package org.javaacademy.onlinebanking.enums;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public enum OperationType {
    WITHDRAW("Списание"),
    DEPOSIT("Зачисление");

    private final String operation;
}

package org.javaacademy.onlinebanking.enums;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public enum OperationType {
    DEBETING("Списание"),
    CREDITING("Зачисление");

    private final String operation;
}

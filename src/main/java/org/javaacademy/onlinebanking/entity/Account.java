package org.javaacademy.onlinebanking.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@RequiredArgsConstructor
public class Account {
    @NonNull
    private String accountId;
    @NonNull
    private User user;
    @Setter
    private BigDecimal amount = BigDecimal.ZERO;

    public BigDecimal getAmount() {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }
}



package org.javaacademy.onlinebanking.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class Account {
    @NonNull
    private String id;
    @NonNull
    private User user;
    @Setter
    private BigDecimal amount = BigDecimal.ZERO;
}

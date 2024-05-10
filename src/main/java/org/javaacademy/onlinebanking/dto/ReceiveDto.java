package org.javaacademy.onlinebanking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для пополнения счета")
public class ReceiveDto {
    private BigDecimal amount;
    private String accountId;
    private String description;
}

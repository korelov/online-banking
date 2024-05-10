package org.javaacademy.onlinebanking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для проведения платежей")
public class PayDto {
    @Schema(description = "Токен пользователя")
    private String token;
    @Schema(description = "Сумма")
    private BigDecimal amount;
    @Schema(description = "Номер пользовательского счета")
    private String accountId;
    @Schema(description = "Описание")
    private String description;
}

package org.javaacademy.onlinebanking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "PIN-код")
public class PinDto {
    @Schema(description = "Идентификатор", example = "число из 4 цифр")
    private String pin;
}

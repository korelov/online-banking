package org.javaacademy.onlinebanking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность для Регистрации пользователя")
public class UserDto {
    private String phone;
    private String fullName;
}

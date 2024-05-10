package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    /**
     * У сервиса аутентификации есть метод - добавить новую запись.
     * Добавляется пара уникальный идентификатор + пинкод (число из 4 цифр).
     *
     * @param userId Уникальный идентификатор пользователя
     * @param pin    Пин-код
     */
    public void addUserAuth(UUID userId, String pin) {
        authenticationRepository.addUserAuth(userId, pin);
    }

    /**
     * У сервиса аутентификации есть метод - аутентификация: на вход передается уникальный идентификатор,
     * пинкод. На выход передается результат проверки: есть запись с таким уникальным идентификатором и
     * пинкод по этому идентификатору совпадает с переданным пинкодом.
     *
     * @param userId Уникальный идентификатор пользователя
     * @param pin    Пин-код
     * @return Результат аутентификации
     */
    public boolean authentication(UUID userId, String pin) {
        return authenticationRepository.authentication(userId, pin);
    }
}

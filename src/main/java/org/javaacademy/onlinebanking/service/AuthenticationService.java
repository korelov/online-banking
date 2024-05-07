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
     * Добавляется пара уникальный индентификатор + пинкод (число из 4 цифр).
     *
     * @param userId
     * @param pin
     */
    public void addUserAuth(UUID userId, String pin) {
        authenticationRepository.addUserAuth(userId, pin);
    }

    /**
     * У сервиса аутентификации есть метод - аутентификация: на вход передается уникальный индентификатор, пинкод.
     * На выход передается результат проверки: есть запись с таким уникальным индентификатором и пинкод
     * по этому идентификатору совпадает с переданным пинкодом.
     *
     * @param userId
     * @param pin
     * @return
     */
    public boolean authentication(UUID userId, String pin) {
        return authenticationRepository.authentication(userId, pin);
    }
}

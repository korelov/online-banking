package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.PinDto;
import org.javaacademy.onlinebanking.dto.TokenDto;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.exception.UserAlreadyExistException;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    /**
     * Регистрация пользователя: передается телефон, фио владельца.
     * Возврашает случайный пинкод из 4 цифр (ТЕКСТ! "0001").
     *
     * @param phone    телефон
     * @param fullName ФИО
     * @return Пин код
     */
    public PinDto registerUser(String phone, String fullName) {
        User existingUser = userRepository.findByPhone(phone);
        if (existingUser != null) {
            throw new UserAlreadyExistException("Пользователь с таким телефоном уже зарегистрирован");
        }
        UUID uuid = UUID.randomUUID();
        User user = new User(phone, uuid, fullName);
        String pin = String.valueOf(new Random().nextInt(9000) + 1000);
        authenticationService.addUserAuth(user.getId(), pin);
        userRepository.addUser(user);
        return new PinDto(pin);
    }

    /**
     * Аутентификация пользователя: передается телефон и пинкод. Возвращается сгенерированный токен.
     *
     * @param phone телефон
     * @param pin   Пин-код
     */
    public TokenDto authenticateUser(String phone, String pin) {
        User existingUser = userRepository.findByPhone(phone);
        if (existingUser == null) {
            throw new RuntimeException("Пользователь с таким номером телефона не найден");
        }
        UUID existingUserId = existingUser.getId();
        boolean authentication = authenticationService.authentication(existingUserId, pin);
        if (!authentication) {
            throw new RuntimeException("Некорректный пин-код");
        }
        return new TokenDto(tokenService.createToken(existingUserId));
    }

    /**
     * Нахождение пользователя по токену (на вход токен).
     *
     * @param token уникальный токен
     * @return пользователь
     */
    public User getUserByToken(String token) {
        UUID userId = UUID.fromString(tokenService.deserializeToken(token));
        return userRepository.findById(userId);
    }
}

package org.javaacademy.onlinebanking.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class AuthenticationRepository {
    private Map<UUID, String> authMap = new HashMap<>();

    public void addUserAuth(UUID userId, String pin) {
        authMap.put(userId, pin);
    }

    public boolean authentication(UUID userId, String pin) {
        return Objects.equals(authMap.get(userId), pin);
    }
}

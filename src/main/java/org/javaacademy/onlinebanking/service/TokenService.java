package org.javaacademy.onlinebanking.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    private static final String PREFIX = "online";
    private static final String POSTFIX = "token";

    public String createToken(UUID existingUserId) {
        return PREFIX + existingUserId + POSTFIX;
    }

    public String deserializeToken(String token) {
        return token.substring("online".length(), token.indexOf("token"));
    }
}

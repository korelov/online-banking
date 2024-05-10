package org.javaacademy.onlinebanking.repository;

import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UserRepository {
    private Map<String, User> data = new HashMap<>();

    public User findByPhone(String phone) {
        return data.get(phone);
    }

    public void addUser(User user) {
        data.put(user.getPhone(), user);
    }

    public User findById(UUID userId) {
        return data.values().stream().filter(user -> user.getId().equals(userId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Пользователь с таким идентификатором не существует"));
    }

    public void clear() {
        data.clear();
    }
}

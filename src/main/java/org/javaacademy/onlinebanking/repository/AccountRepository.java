package org.javaacademy.onlinebanking.repository;

import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccountRepository {
    private Map<String, Account> accounts = new HashMap<>();

    public boolean checkUniqueId(String id) {
        return accounts.containsKey(id);
    }

    /**
     * Добавление счета
     *
     * @param account
     */
    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    /**
     * Поиск счета по номеру
     *
     * @param id
     * @return
     */
    public Account findById(String id) {
        return accounts.get(id);
    }

    /**
     * Получение всех счетов у пользователя
     *
     * @param user
     * @return
     */
    public List<Account> getAllAccountsByUser(User user) {
        return accounts.values().stream()
                .filter(account -> account.getUser().equals(user))
                .toList();
    }
}

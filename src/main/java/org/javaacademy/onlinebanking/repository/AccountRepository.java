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
     * @param account Счет
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    /**
     * Поиск счета по номеру
     *
     * @param accountId Номер счета
     * @return Вернет счет
     */
    public Account findById(String accountId) {
        return accounts.get(accountId);
    }

    /**
     * Получение всех счетов у пользователя
     *
     * @param user Пользователь
     * @return Список счетов пользователя
     */
    public List<Account> getAllAccountsByUser(User user) {
        return accounts.values().stream()
                .filter(account -> account.getUser().equals(user))
                .toList();
    }
}

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

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account findById(String accountId) {
        return accounts.get(accountId);
    }

    public List<Account> getAllAccountsByUser(User user) {
        return accounts.values().stream()
                .filter(account -> account.getUser().equals(user))
                .toList();
    }
}

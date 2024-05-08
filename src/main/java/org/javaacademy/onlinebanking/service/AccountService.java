package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Создать метод генерации номера счета. На выход сгенерированная строка:
     * "[6 цифр]". Например: "000001"
     * Номера счетов уникальны (не могут повторяться).
     *
     * @return
     */
    private String accountIdGenerator() {
        String accountId = numberRandomizer();
        while (accountRepository.checkUniqueId(accountId)) {
            accountId = numberRandomizer();
        }
        return accountId;
    }

    private String numberRandomizer() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    /**
     * Создать счет для пользователя - на вход пользователь: создается счет с остатком 0.00.
     *
     * @param user
     */
    public String createAccountForUser(User user) {
        String accountId = accountIdGenerator();
        Account account = new Account(accountId, user);
        accountRepository.addAccount(account);
        return accountId;
    }

    /**
     * Занести деньги на счет (номер счета, сумма).
     * Увеличивает остаток счета. Если счета не существует - ошибка.
     *
     * @param accountId
     * @param amount
     */
    public void deposit(String accountId, BigDecimal amount) {
        Account account = findById(accountId);
        account.setAmount(account.getAmount().add(amount));
    }

    /**
     * Списать деньги со счета (номер счета, сумма). Уменьшает остаток счета.
     * Остаток после операции не может быть отрицательный.
     * Если счета не существует - ошибка.
     *
     * @param accountId
     * @param amount
     */
    public void withdrawAmount(String accountId, BigDecimal amount) {
        Account account = findById(accountId);
        BigDecimal totalAmount = account.getAmount();
        totalAmount = totalAmount.subtract(amount);
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Остаток после операции не может быть отрицательный");
        }
        account.setAmount(totalAmount);
    }

    /**
     * Получить счета пользователя (на вход пользователь), на выход список его счетов.
     *
     * @param user
     * @return
     */
    public List<Account> getUserAccounts(User user) {
        return accountRepository.getAllAccountsByUser(user);
    }

    /**
     * Получить остаток по счету(номер счета). На выход остаток.
     * Если счета не существует - ошибка.
     *
     * @return
     */
    public BigDecimal getAccountBalance(String accountId) {
        return findById(accountId).getAmount();
    }

    /**
     * Проверка принадлежности счета к пользователю.
     * На вход пользователь и номер счета. Возвращает принадлежит или нет.
     *
     * @param user
     * @param accountId
     * @return
     */
    public boolean checkAccountBelongsToUser(User user, String accountId) {
        return accountRepository.findById(accountId).getUser().equals(user);
    }

    private Account findById(String accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new RuntimeException("Счета не существует");
        }
        return account;
    }
}

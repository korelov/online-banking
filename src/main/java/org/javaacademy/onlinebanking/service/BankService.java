package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankConfiguration;
import org.javaacademy.onlinebanking.entity.FinancialOperation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.enums.FinancialOperationType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {
    private final UserService userService;
    private final AccountService accountService;
    private final FinancialOperationService financialOperationService;
    private final InterBankTransfersService interBankTransfersService;
    private final BankConfiguration bankConfiguration;

    /**
     * Делаем платеж: на вход - номер счета, сумма, описание, токен. На основании токена получаем пользователя.
     * Проверяем что счет принадлежит пользователю(этап 2 пункт2.7): если счет не принадлежит пользователю,
     * выкидываем ошибку. Производит списание со счета (2 этап 2.4 пункт). Записывает операцию в историю.
     *
     * @param accountId   Номер счета
     * @param amount      Сумма списания
     * @param description Описание операции
     * @param token       Пользовательский токен
     */
    public void makePayment(String accountId, BigDecimal amount, String description, String token) {
        // Проверяем токен, получаем пользователя
        User userByToken = userService.getUserByToken(token);
        // Проверяем, что счет принадлежит пользователю
        checkAccountInBuUser(accountId, userByToken);
        // Списание со счета
        accountService.withdrawAmount(accountId, amount);
        // Создаем финансовую операцию
        FinancialOperation financialOperation = createFinancialOperation(
                accountId,
                amount,
                description,
                FinancialOperationType.WITHDRAW);
        // Запись операции в историю
        financialOperationService.addOperation(financialOperation);
    }

    private static FinancialOperation createFinancialOperation(String accountId,
                                                               BigDecimal amount,
                                                               String description,
                                                               FinancialOperationType financialOperationType) {
        UUID uuid = UUID.randomUUID();
        return new FinancialOperation(uuid, accountId, financialOperationType, amount, description);
    }

    /**
     * Проверяем, что пользователь является владельцем счета.
     *
     * @param accountId   Номер счета
     * @param userByToken Пользователь найденный по токену
     */
    private void checkAccountInBuUser(String accountId, User userByToken) {
        boolean checkAccountBelongsToUser = accountService.checkAccountBelongsToUser(userByToken, accountId);
        if (!checkAccountBelongsToUser) {
            throw new RuntimeException("Счет не принадлежит пользователю");
        }
    }

    /**
     * Показывать всю историю платежей по пользователю
     * На выход история операция по всем счетам пользователя в отсортированной по дате.
     *
     * @param token Пользовательский токен
     * @return История операция по всем счетам пользователя в отсортированной по дате.
     */
    public List<FinancialOperation> getPaymentHistory(String token) {
        // Получаем пользователя по токену
        User userByToken = userService.getUserByToken(token);
        // Возвращаем историю операций пользователя
        return financialOperationService.getOperationsByUser(userByToken);
    }

    /**
     * Пополнение счета и записываем финансовую операцию в историю
     *
     * @param accountId   Номер счета
     * @param amount      Сумма к пополнению
     * @param description Описание
     */
    public void makeDeposit(String accountId, BigDecimal amount, String description) {
        // Зачисление на счет
        accountService.deposit(accountId, amount);
        FinancialOperation financialOperation = createFinancialOperation(
                accountId, amount, description, FinancialOperationType.DEPOSIT);
        // Запись операции в историю
        financialOperationService.addOperation(financialOperation);
    }

    /**
     * Межбанковский перевод
     *
     * @param token       Пользовательский токен
     * @param amount      Сумма перевода
     * @param description Описание
     * @param accountId   Номер счета
     */
    public void transferToAnotherBank(String token,
                                      BigDecimal amount,
                                      String description,
                                      String accountId) {
        // Получили пользователя по токену
        User userByToken = userService.getUserByToken(token);
        // Проверили, что пользователь является владельцем счета
        checkAccountInBuUser(accountId, userByToken);
        // Списание со счета
        accountService.withdrawAmount(accountId, amount);
        // Создание записи об операции
        FinancialOperation financialOperation = createFinancialOperation(
                accountId, amount, description, FinancialOperationType.TRANSFER);
        // Запись операции в историю
        financialOperationService.addOperation(financialOperation);
        //перевод в другой банк
        interBankTransfersService.transferToAnotherBank(bankConfiguration.getBankName(),
                amount,
                description,
                userByToken.getFullName());
    }
}


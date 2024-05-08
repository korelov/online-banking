package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.enums.OperationType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {
    private final UserService userService;
    private final AccountService accountService;
    private final OperationService operationService;

    /**
     * 3.1. Делать платеж: на вход - номер счета, сумма, описание, токен. На основании токена получаем пользователя.
     * Проверяем что счет принадлежит пользователю(этап 2 пункт2.7): если счет не принадлежит пользователю,
     * выкидываем ошибку. Производит списание со счета (2 этап 2.4 пункт). Записывает операцию в историю.
     *
     * @param accountId
     * @param amount
     * @param description
     * @param token
     */
    public void makePayment(String accountId, BigDecimal amount, String description, String token) {
        // Проверяем токен, получаем пользователя
        User userByToken = userService.getUserByToken(token);
        // Проверяем, что счет принадлежит пользователю
        boolean checkAccountBelongsToUser = accountService.checkAccountBelongsToUser(userByToken, accountId);
        if (!checkAccountBelongsToUser) {
            throw new RuntimeException("Счет не принадлежит пользователю");
        }
        // Списание со счета
        accountService.withdrawAmount(accountId, amount);
        UUID uuid = UUID.randomUUID();
        Operation operation = new Operation(uuid,
                accountId,
                OperationType.WITHDRAW,
                amount,
                description);
        // Запись операции в историю
        operationService.addOperation(operation);
    }

    /**
     * 3.2. Показывать всю историю платежей по пользователю: на вход токен.
     * На выход история операция по всем счетам пользователя в отсортированной по дате.
     * По токену получить пользователя, дальше пункт 2.3.
     *
     * @param token
     * @return
     */
    public List<Operation> getPaymentHistory(String token) {
        // Получаем пользователя по токену
        User userByToken = userService.getUserByToken(token);
        // Возвращаем историю операций пользователя
        return operationService.getOperationsByUser(userByToken);
    }

    /**
     * 3.3. Делать засчисление: на вход - номер счета, сумма, описание.
     * Зачисляет на счет деньги и записывает операцию в истори
     *
     * @param accountId
     * @param amount
     * @param description
     */
    public void makeDeposit(String accountId, BigDecimal amount, String description) {
        // Зачисление на счет
        accountService.deposit(accountId, amount);
        UUID uuid = UUID.randomUUID();
        Operation operation = new Operation(uuid,
                accountId,
                OperationType.DEPOSIT,
                amount,
                description);
        // Запись операции в историю
        operationService.addOperation(operation);
    }

}


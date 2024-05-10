package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.FinancialOperation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialOperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;

    /**
     * Показывать все операции по счету. Порядок операций по дате (сначала новые, потом более старые).
     *
     * @param accountId Номер счета
     * @return Список операций по счету
     */
    private List<FinancialOperation> getOperationsByAccount(String accountId) {
        return operationRepository.getOperationsData().values().stream()
                .filter(financialOperation -> financialOperation.getAccountId().equals(accountId)).toList();
    }

    /**
     * Добавление новой операции
     *
     * @param financialOperation Финансовая операция
     */
    public void addOperation(FinancialOperation financialOperation) {
        operationRepository.add(financialOperation);
    }

    /**
     * Показывать все операции для пользователя. На вход пользователь.
     * Порядок операций по дате (сначала новые, потом более старые).
     *
     * @param user Пользователь
     * @return Все операции пользователя
     */
    public List<FinancialOperation> getOperationsByUser(User user) {
        List<Account> userAccounts = accountService.getUserAccounts(user);
        return userAccounts.stream()
                .map(Account::getAccountId)
                .flatMap(accountId -> getOperationsByAccount(accountId)
                        .stream())
                .collect(Collectors.toList());
    }
}

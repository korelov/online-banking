package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.entity.Account;
import org.javaacademy.onlinebanking.entity.Operation;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;

    /**
     * 2.1 Показывать все операции по счету.
     * Порядок операций по дате (сначала новые, потом более старые).
     *
     * @param accountId
     * @return
     */
    private List<Operation> getOperationsByAccount(String accountId) {
        return operationRepository.getOperationsData().values().stream()
                .filter(operation -> operation.getAccountId().equals(accountId)).toList();
    }

    /**
     * 2.2. Заносить новую операцию.
     *
     * @param operation
     */
    public void addOperation(Operation operation) {
        operationRepository.add(operation);
    }

    /**
     * 2.3 Показывать все операции для пользователя. На вход пользователь.
     * Получаем счета пользователя (этап2 пункт 2.5), ищем по этим счетам операции.
     * Порядок операций по дате (сначала новые, потом более старые).
     *
     * @param user
     * @return
     */
    public List<Operation> getOperationsByUser(User user) {
        List<Account> userAccounts = accountService.getUserAccounts(user);
        return userAccounts.stream()
                .map(Account::getAccountId)
                .flatMap(accountId -> getOperationsByAccount(accountId)
                        .stream())
                .collect(Collectors.toList());
    }
}

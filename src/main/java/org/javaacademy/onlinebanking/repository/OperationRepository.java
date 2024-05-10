package org.javaacademy.onlinebanking.repository;

import org.javaacademy.onlinebanking.entity.FinancialOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class OperationRepository {
    private SortedMap<LocalDateTime, FinancialOperation> operationsData = new TreeMap<>();

    public void add(FinancialOperation financialOperation) {
        operationsData.put(financialOperation.getLocalDateTime(), financialOperation);
    }

    public SortedMap<LocalDateTime, FinancialOperation> getOperationsData() {
        return operationsData;
    }
}

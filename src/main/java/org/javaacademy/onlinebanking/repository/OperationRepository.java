package org.javaacademy.onlinebanking.repository;

import org.javaacademy.onlinebanking.entity.Operation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class OperationRepository {
    private SortedMap<LocalDateTime,Operation> operationsData =  new TreeMap<>();

    public void add(Operation operation) {
        operationsData.put(operation.getLocalDateTime(), operation);
    }

    public SortedMap<LocalDateTime, Operation> getOperationsData() {
        return operationsData;
    }
}

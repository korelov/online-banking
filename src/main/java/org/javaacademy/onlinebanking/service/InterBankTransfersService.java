package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankConfiguration;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InterBankTransfersService {
    private final BankConfiguration bankConfiguration;

    public void transferToAnotherBank(String senderBankName,
                                      BigDecimal amount,
                                      String description,
                                      String senderFullName) {
        Map<String, Object> data = new HashMap<>();
        data.put("senderBankName", bankConfiguration.getBankName());
        data.put("amount", amount);
        data.put("description", description);
        data.put("senderFullName", senderFullName);
        String partnerUrl = bankConfiguration.getPartnerUrl() + "/operations/receive";
        RequestEntity<Map<String, Object>> request = RequestEntity
                .post(partnerUrl)
                .body(data);
    }
}

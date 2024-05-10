package org.javaacademy.onlinebanking.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.config.BankConfiguration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InterBankTransfersService {
    private final BankConfiguration bankConfiguration;

    public void transferToAnotherBank(String receiverBankName,
                                      BigDecimal amount,
                                      String description,
                                      String senderFullName) {
        String partnerUrl = bankConfiguration.getPartnerUrl();


    }



}

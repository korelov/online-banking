package org.javaacademy.onlinebanking.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BankConfiguration {
    @Value("${bank.name}")
    private String bankName;
    @Value("${partner.url}")
    private String partnerUrl;
}

package org.javaacademy.onlinebanking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.PayDto;
import org.javaacademy.onlinebanking.dto.ReceiveDto;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
public class FinancialOperationControllerTest extends AbstractIntegrationTest {
    private static final int PORT = 8003;
    private static final String PATH = "/api/bank";
    private static final String OPERATIONS_RECEIVE = "/operations/receive";
    private static final String OPERATIONS_PAY = "/operations/pay";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.clear();
        authUser();
        createAccountByUser();
    }

    @Test
    public void makeDepositSuccess() {
        String accountId = readAccountId();
        ReceiveDto receiveDto = new ReceiveDto(
                new BigDecimal(1000), accountId, "Пополнение счета");
        Response post = RestAssured
                .given()
                .body(receiveDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + OPERATIONS_RECEIVE);
        post.then().log().all();
        post.then().statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    public void makePaymentNotSuccess() {
        String token = readUserToken().getToken();
        String accountId = readAccountId();
        PayDto payDto = new PayDto(token, new BigDecimal(100), accountId, "Делаем платеж");
        Response post = RestAssured
                .given()
                .body(payDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + OPERATIONS_PAY);
        post.then().log().all();
        post.then().statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void makePaymentSuccess() {
        String token = readUserToken().getToken();
        String accountId = readAccountId();
        makeDeposit();
        PayDto payDto = new PayDto(token, new BigDecimal(100), accountId, "Делаем платеж");
        Response post = RestAssured
                .given()
                .body(payDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + OPERATIONS_PAY);
        post.then().log().all();
        post.then().statusCode(HttpStatus.ACCEPTED.value());
    }

}

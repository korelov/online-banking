package org.javaacademy.onlinebanking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.onlinebanking.dto.TokenDto;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.stream.IntStream;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
public class AccountControllerTest extends AbstractIntegrationTest {
    private static final int PORT = 8003;
    private static final String PATH = "/api/bank";
    private static final String ACCOUNT = "/account";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.clear();
        authUser();
    }

    @Test
    @SneakyThrows
    public void createUserAccountSuccess() {
        TokenDto tokenDto = readUserToken();
        Response post = RestAssured
                .given()
                .body(tokenDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + ACCOUNT);
        Assertions.assertEquals(201, post.statusCode());
        String accountId = post.asPrettyString();
        writeAccountId(accountId);
        post.then().log().all();
    }

    @Test
    public void getAllUserAccountSuccess() {
        TokenDto tokenDto = readUserToken();
        IntStream.range(0, 10).forEach(i -> createAccountByUser());
        ValidatableResponse response = RestAssured.
                given()
                .param("token", tokenDto.getToken()) // Use param with explicit key
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .get(PATH + ACCOUNT) // Assuming accountId is a path parameter
                .then()
                .statusCode(HttpStatus.OK.value()) // Assert status code
                .log().all();
    }

    @Test
    public void getBalanceSuccess() {
        createAccountByUser();
        String accountId = readAccountId();
        System.out.println(accountId);
        RestAssured.given().log().all()
                .port(PORT)
                .get(PATH + ACCOUNT + "/{accountId}", accountId)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .log().ifValidationFails();
    }
}

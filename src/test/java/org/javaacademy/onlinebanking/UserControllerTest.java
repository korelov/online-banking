package org.javaacademy.onlinebanking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebanking.dto.SignInDto;
import org.javaacademy.onlinebanking.dto.UserDto;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
public class UserControllerTest extends AbstractIntegrationTest {
    private static final int PORT = 8003;
    private static final String PATH = "/api/bank";
    private static final String USER_SIGNUP = "/user/signup";
    private static final String USER_AUTH = "/user/auth";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.clear();
    }

    @Test
    public void createUserSuccess() {
        UserDto userDto = readUserDto();
        Response post = RestAssured
                .given()
                .body(userDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + USER_SIGNUP);
        Assertions.assertEquals(201, post.statusCode());
        post.then().log().all();
    }

    @Test
    public void authUserSuccess() {
        createUser();
        SignInDto signInDto = readSignInDto();
        Response post = RestAssured
                .given()
                .body(signInDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + USER_AUTH);
        Assertions.assertEquals(202, post.statusCode());
        post.then().log().all();
    }
}
package org.javaacademy.onlinebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.AuthenticationRepository;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

@AutoConfigureMockMvc
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
public class UserControllerTest {
    private static final String TEST_RESOURCES_USER_SIGNUP_DATA_JSON = "src/test/resources/user_signup_data.json";
    private static final String TEST_RESOURCES_USER_AUTH_DATA_JSON = "src/test/resources/user_auth_data.json";
    private static final int PORT = 8003;
    private static final String PATH = "/api/bank/user";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @BeforeEach
    public void init() {
        userRepository.clear();
    }


    @SneakyThrows
    private void createUser() {

        String userDto = readAllLines("user_signup_data.json");
        //  UserDto userDto = new UserDto("1234567890", "иванов иван иванович");

//        System.out.println(userDto);
//        Response post = RestAssured
//                .given()
//                .body(userDto)
//                .log().all()
//                .contentType(ContentType.JSON)
//                .port(PORT)
//                .post(PATH + "/signup")
//                .then()
//                .log().all()
//                .extract().response();

        RestAssured
                .given()
                .body(userDto)
                .log().all()
                .contentType(ContentType.JSON)
                .port(PORT)
                .post(PATH + "/signup")
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value());

       // String pin = post.getBody().asPrettyString();

    }


//    private void writePin(SingInDto singInDto) {
//        try {
//            objectMapper.writeValue(new File(TEST_RESOURCES_USER_AUTH_DATA_JSON), singInDto);
//        } catch (IOException e) {
//            System.err.println("Ошибка записи json data !" + e.getMessage());
//        }
//    }

    @Test
    public void signUpUserSuccess() {
        createUser();
        String phone = "1234567890";
        User userByPhone = userRepository.findByPhone(phone);
        Assertions.assertEquals(phone, userByPhone.getPhone());
    }

//    @Test
//    public void authUser() throws JsonProcessingException {
//        createUser();
//        SingInDto singInDto = objectMapper.
//                readValue(readData(TEST_RESOURCES_USER_AUTH_DATA_JSON), SingInDto.class);
//        Response post = RestAssured
//                .given()
//                .contentType(ContentType.JSON)
//                .body(singInDto)
//                .port(PORT)
//                .log().all()
//                .when()
//                .post(PATH + "/auth");
//        post.then().statusCode(HttpStatus.ACCEPTED.value());
//        String token = post.getBody().asPrettyString();
//        System.out.println(token);
//    }
//


//    private void createUser() throws JsonProcessingException {
//        UserDto userDto = objectMapper.
//                readValue(readData(TEST_RESOURCES_USER_SIGNUP_DATA_JSON), UserDto.class);
//        Response post = RestAssured
//                .given()
//                .contentType(ContentType.JSON)
//                .body(userDto)
//                .port(PORT)
//                .log().all()
//                .when()
//                .post(PATH + "/signup");
//        String pin = post.getBody().asPrettyString();
//        Assertions.assertEquals(4, pin.length());
//        writePin(new SingInDto(userDto.getPhone(), pin));
//    }

    @SneakyThrows
    private String readAllLines(String filename) {
        URL url = this.getClass().getClassLoader().getResource(filename);

        Scanner scanner = new Scanner(new File(url.toURI()));
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine())
                    .append("\n");
        }
        return builder.toString();
    }
}


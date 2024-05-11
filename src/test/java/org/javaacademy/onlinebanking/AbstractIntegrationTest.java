package org.javaacademy.onlinebanking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.onlinebanking.dto.PinDto;
import org.javaacademy.onlinebanking.dto.SignInDto;
import org.javaacademy.onlinebanking.dto.TokenDto;
import org.javaacademy.onlinebanking.dto.UserDto;
import org.javaacademy.onlinebanking.entity.User;
import org.javaacademy.onlinebanking.repository.UserRepository;
import org.javaacademy.onlinebanking.service.AccountService;
import org.javaacademy.onlinebanking.service.BankService;
import org.javaacademy.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@NoArgsConstructor
@Slf4j
public class AbstractIntegrationTest {
    private static final String TEST_RESOURCES_USER_SIGNUP_DATA_JSON = "src/test/resources/user_signup_data.json";
    private static final String TEST_RESOURCES_USER_AUTH_DATA_JSON = "src/test/resources/user_auth_data.json";
    private static final String TEST_RESOURCES_TOKEN_DATA_JSON = "src/test/resources/token_data.json";
    private static final String TEST_RESOURCES_ACCOUNT_DATA_JSON = "src/test/resources/account_data.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankService bankService;

    @SneakyThrows
    private Map<String, Object> readTestDataJson(String pathFile) {
        return objectMapper.readValue(new File(pathFile), Map.class);
    }

    @SneakyThrows
    public void createUser() {
        UserDto userDto = readUserDto();
        PinDto pinDto = userService.registerUser(userDto.getPhone(), userDto.getFullName());
        SignInDto signInDto = new SignInDto(userDto.getPhone(), pinDto.getPin());
        objectMapper.writeValue(new FileWriter(TEST_RESOURCES_USER_AUTH_DATA_JSON), signInDto);
    }

    @SneakyThrows
    public void authUser() {
        createUser();
        SignInDto signInDto = readSignInDto();
        TokenDto tokenDto = userService.authenticateUser(signInDto.getPhone(), signInDto.getPin());
        objectMapper.writeValue(new FileWriter(TEST_RESOURCES_TOKEN_DATA_JSON), tokenDto);
    }

    public TokenDto readUserToken() {
        return objectMapper.convertValue(readTestDataJson(TEST_RESOURCES_TOKEN_DATA_JSON), TokenDto.class);
    }

    @SneakyThrows
    public String readAccountId() {
        return new String(Files.readAllBytes(Paths.get(TEST_RESOURCES_ACCOUNT_DATA_JSON)));
    }

    @SneakyThrows
    public void writeAccountId(String accountId) {
        try (FileWriter fileWriter = new FileWriter(new File(TEST_RESOURCES_ACCOUNT_DATA_JSON))) {
            fileWriter.write(accountId);
        }
    }

    public UserDto readUserDto() {
        return objectMapper.convertValue(
                readTestDataJson(TEST_RESOURCES_USER_SIGNUP_DATA_JSON), UserDto.class);
    }

    public SignInDto readSignInDto() {
        return objectMapper
                .convertValue(readTestDataJson(TEST_RESOURCES_USER_AUTH_DATA_JSON), SignInDto.class);
    }

    public void createAccountByUser() {
        User byPhone = userService.getUserByToken(readUserToken().getToken());
        String accountId = accountService.createAccountForUser(byPhone);
        writeAccountId(accountId);
    }

    public void makeDeposit() {
        String accountId = readAccountId();
        BigDecimal amount = new BigDecimal(1000);
        String description = "Делаем депозит";
        bankService.makeDeposit(accountId, amount, description);
    }
}



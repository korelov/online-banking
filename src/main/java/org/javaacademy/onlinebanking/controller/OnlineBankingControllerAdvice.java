package org.javaacademy.onlinebanking.controller;

import org.javaacademy.onlinebanking.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OnlineBankingControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> userAlreadyExistException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Пользователь с таким телефоном уже зарегистрирован!");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runTimeException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("У нас произошла ошибка, уже работаем над ней.");
    }
}

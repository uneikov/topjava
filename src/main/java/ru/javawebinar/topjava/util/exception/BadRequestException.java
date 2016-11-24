package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad form data")  // 400
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}

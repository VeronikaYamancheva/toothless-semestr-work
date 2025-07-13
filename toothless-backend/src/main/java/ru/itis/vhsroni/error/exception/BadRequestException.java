package ru.itis.vhsroni.error.exception;

import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.ServiceException;

public class BadRequestException extends ServiceException {

    public BadRequestException(String errorMessage, ErrorCode errorCode) {
        super(HttpStatus.valueOf(400), errorMessage, errorCode);
    }
}

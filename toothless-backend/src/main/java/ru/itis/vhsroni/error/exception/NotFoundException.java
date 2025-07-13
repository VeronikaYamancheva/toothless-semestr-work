package ru.itis.vhsroni.error.exception;

import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(HttpStatus.valueOf(404), message, errorCode);
    }
}


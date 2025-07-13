package ru.itis.vhsroni.error.exception;

import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;

public class UnauthorizedException extends ServiceException{

    public UnauthorizedException(String errorMessage, ErrorCode errorCode) {
        super(HttpStatus.valueOf(401), errorMessage, errorCode);
    }
}

package ru.itis.vhsroni.error.exception;

import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;

public class InternalException extends ServiceException{

    public InternalException(String errorMessage, ErrorCode errorCode) {
        super(HttpStatus.valueOf(500), errorMessage, errorCode);
    }
}

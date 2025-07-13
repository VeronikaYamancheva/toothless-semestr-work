package ru.itis.vhsroni.error.exception;

import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.ServiceException;

public class ForbiddenException extends ServiceException {

    public ForbiddenException(String errorMessage, ErrorCode errorCode) {
        super(HttpStatus.valueOf(403), errorMessage, errorCode);
    }
}

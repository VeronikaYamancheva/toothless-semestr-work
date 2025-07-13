package ru.itis.vhsroni.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.itis.vhsroni.error.enums.ErrorCode;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;

    private final ErrorCode errorCode;

    public ServiceException(HttpStatus status, String message, ErrorCode errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("ServiceException: status=%s, code=%s, message=%s",
                status, errorCode, getMessage());
    }
}

package ru.itis.vhsroni.error.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.dto.ValidationErrorResponse;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.*;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMessageProperties errorMessage;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ValidationErrorResponse.ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        log.info("Validation failed: fields={}, errorCount={}",
                errors.stream().map(ValidationErrorResponse.ValidationError::field).collect(Collectors.toSet()),
                errors.size());
        return ValidationErrorResponse.builder()
                .code(400)
                .exceptionMessage(errorMessage.invalidInputs())
                .errorCode(ErrorCode.INVALID_INPUT_DATA)
                .validationErrors(errors)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse handleBadRequestException(BadRequestException ex) {
        log.warn("Bad request exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return ValidationErrorResponse.builder()
                .code(400)
                .exceptionMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiErrorResponse handleNotFoundException(NotFoundException ex) {
        log.warn("Resource not found exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return ApiErrorResponse.builder()
                .code(404)
                .exceptionMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ApiErrorResponse handleForbiddenException(ForbiddenException ex) {
        log.warn("Access denied exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return ApiErrorResponse.builder()
                .code(403)
                .exceptionMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ApiErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        log.warn("Unauthorized exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return ApiErrorResponse.builder()
                .code(401)
                .exceptionMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .build();
    }

    @ExceptionHandler(InternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiErrorResponse handleInternalException(InternalException ex){
        log.error("Internal error: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return ApiErrorResponse.builder()
                .code(500)
                .exceptionMessage(errorMessage.internalError())
                .errorCode(ex.getErrorCode())
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    ResponseEntity<ApiErrorResponse> handleServiceException(ServiceException ex) {
        log.warn("Service exception: status={}, code={}, message={}", ex.getStatus().value(), ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(ApiErrorResponse.builder()
                        .code(ex.getStatus().value())
                        .exceptionMessage(ex.getMessage())
                        .errorCode(ex.getErrorCode())
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiErrorResponse handleGeneralException(Exception ex) {
        log.error("Unhandled error: type={}, message={}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ApiErrorResponse.builder()
                .code(500)
                .exceptionMessage(errorMessage.internalError())
                .errorCode(ErrorCode.INTERNAL)
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ApiErrorResponse.builder()
                .code(403)
                .exceptionMessage(errorMessage.forbidden())
                .errorCode(ErrorCode.FORBIDDEN)
                .build();
    }

}
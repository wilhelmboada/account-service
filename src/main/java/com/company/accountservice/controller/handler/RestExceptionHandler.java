package com.company.accountservice.controller.handler;

import com.company.accountservice.domain.error.BusinessError;
import com.company.accountservice.controller.errors.Error;
import com.company.accountservice.controller.errors.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> error = processErrors(ex);
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(error)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private List<Error> processErrors(MethodArgumentNotValidException e) {
        List<Error> errorList = new ArrayList<>();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            Error error = createErrorResponse(
                    zonedDateTime,
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    fieldError.getField() + " " + fieldError.getDefaultMessage()
            );
            errorList.add(error);
        }
        return errorList;
    }

    @ExceptionHandler(value = BusinessError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(BusinessError e) {
        Error errorResponse = createErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        List<Error> error = List.of(errorResponse);
        return ErrorResponse
                .builder()
                .error(error)
                .build();
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleException(MalformedJwtException e) {
        Error errorResponse = createErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid token"
        );
        List<Error> error = List.of(errorResponse);
        return ErrorResponse
                .builder()
                .error(error)
                .build();
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MissingRequestHeaderException e) {
        Error errorResponse = createErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        List<Error> error = List.of(errorResponse);
        return ErrorResponse
                .builder()
                .error(error)
                .build();
    }

    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(SignatureException e) {
        Error errorResponse = createErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        List<Error> error = List.of(errorResponse);
        return ErrorResponse
                .builder()
                .error(error)
                .build();
    }

    private Error createErrorResponse(
            ZonedDateTime zonedDateTime,
            int code,
            String detail) {
        return Error
                .builder()
                .timestamp(zonedDateTime)
                .code(code)
                .detail(detail)
                .build();
    }
}

package com.company.accountservice.controller.handler;

import com.company.accountservice.domain.error.BusinessError;
import com.company.accountservice.controller.errors.Error;
import com.company.accountservice.controller.errors.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@Configuration
public class RestExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleException(MethodArgumentNotValidException e) {
        List<Error> error = processErrors(e);
        return ErrorResponse
                .builder()
                .error(error)
                .build();
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

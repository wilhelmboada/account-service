package com.company.accountservice.controller.handler;

import com.company.accountservice.domain.error.BusinessError;
import com.company.accountservice.controller.errors.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RestExceptionHandlerTest {

    @Test
    void testHandleBusinessError() {
        // Arrange
        RestExceptionHandler handler = new RestExceptionHandler();
        BusinessError businessError = new BusinessError("Business error message");

        // Act
        ErrorResponse response = handler.handleException(businessError);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getError().get(0).getCode());
        assertEquals("Business error message", response.getError().get(0).getDetail());
    }

    @Test
    void testHandleMalformedJwtException() {
        // Arrange
        RestExceptionHandler handler = new RestExceptionHandler();
        MalformedJwtException malformedJwtException = new MalformedJwtException("Invalid token");

        // Act
        ErrorResponse response = handler.handleException(malformedJwtException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getError().get(0).getCode());
        assertEquals("Invalid token", response.getError().get(0).getDetail());
    }
}
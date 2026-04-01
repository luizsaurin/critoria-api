package com.example.critoria.api.advice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BadRequestExceptionTest {
	
	@Test
    void shouldFormatMessageWithArguments() {
        BadRequestException ex = new BadRequestException("Error: {}", "invalid");

        assertEquals("Error: invalid", ex.getMessage());
    }

    @Test
    void shouldHandleMultipleArguments() {
        BadRequestException ex = new BadRequestException("Error: {} - {}", "field", "missing");

        assertEquals("Error: field - missing", ex.getMessage());
    }

    @Test
    void shouldHandleNoArguments() {
        BadRequestException ex = new BadRequestException("Simple error");

        assertEquals("Simple error", ex.getMessage());
    }
}

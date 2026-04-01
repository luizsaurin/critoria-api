package com.example.critoria.api.advice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NotFoundExceptionTest {
	
	@Test
    void shouldFormatMessageWithArguments() {
        NotFoundException ex = new NotFoundException("Resource {} not found", "Movie");

        assertEquals("Resource Movie not found", ex.getMessage());
    }

    @Test
    void shouldHandleNoArguments() {
        NotFoundException ex = new NotFoundException("Not found");

        assertEquals("Not found", ex.getMessage());
    }
}

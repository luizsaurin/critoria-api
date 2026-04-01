package com.example.critoria.api.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.critoria.api.advice.exception.BadRequestException;
import com.example.critoria.api.advice.exception.NotFoundException;
import com.example.critoria.api.constant.ConstraintViolations;
import com.example.critoria.api.dto.ApiErrorResponseDTO;

class ControllerAdviceTest {

	private ControllerAdvice controllerAdvice;
	private BindingResult bindingResult;

	@BeforeEach
	void beforeEach() {
		controllerAdvice = new ControllerAdvice();
		bindingResult = mock(BindingResult.class);
	}

	@Test
	void handleException() {
		Exception ex = new RuntimeException("Unexpected error");

		ResponseEntity<Void> response = controllerAdvice.handleException(ex);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void handleBadRequestException() {
		BadRequestException ex = new BadRequestException("Invalid {}", "input");

		ResponseEntity<ApiErrorResponseDTO> response = controllerAdvice.handleBadRequestException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Invalid input", response.getBody().getMessage());
	}

	@Test
	void handleNotFoundException() {
		NotFoundException ex = new NotFoundException("Not found");

		ResponseEntity<Void> response = controllerAdvice.handleNotFoundException(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@ParameterizedTest
	@MethodSource("handleMethodArgumentNotValidExceptionTestCases")
	void handleMethodArgumentNotValidException(List<FieldError> fieldErrors, String responseMessage) {
		when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

		ResponseEntity<ApiErrorResponseDTO> response = controllerAdvice.handleMethodArgumentNotValidException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(responseMessage, response.getBody().getMessage());
	}

	static Stream<Arguments> handleMethodArgumentNotValidExceptionTestCases() {
		return Stream.of(
				Arguments.of(
						List.of(new FieldError("object", "name", "must not be blank")),
						"name must not be blank"),
						Arguments.of(
					List.of(
						new FieldError("object", "name", "must not be blank"),
						new FieldError("object", "year", "must not be null")),
						"name must not be blank; year must not be null"));
	}

	@ParameterizedTest
	@MethodSource("handleDataIntegrityViolationExceptionTestCases")
	void handleDataIntegrityViolationException(ConstraintViolationException cause, String responseMessage) {
		DataIntegrityViolationException ex = new DataIntegrityViolationException("error", cause);

		ResponseEntity<ApiErrorResponseDTO> response = controllerAdvice.handleDataIntegrityViolationException(ex);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals(responseMessage, response.getBody().getMessage());
	}

	static Stream<Arguments> handleDataIntegrityViolationExceptionTestCases() {

		String constraintError = "constraint error";

		return Stream.of(
				Arguments.of(
						null,
						ConstraintViolations.DEFAULT_MESSAGE),
				Arguments.of(
						new ConstraintViolationException(
								constraintError, null, "unknown_constraint"),
						ConstraintViolations.DEFAULT_MESSAGE),
				Arguments.of(
						new ConstraintViolationException(
								constraintError, null, ConstraintViolations.TITLES_NAME_UNIQUE),
						ConstraintViolations.TITLES_NAME_UNIQUE_MESSAGE),
				Arguments.of(
						new ConstraintViolationException(
								constraintError, null, ConstraintViolations.TITLES_ID_FK),
						ConstraintViolations.TITLES_ID_FK_MESSAGE),
				Arguments.of(
						new ConstraintViolationException(
								constraintError, null, ConstraintViolations.REVIEWS_EMAIL_TITLE_ID_UNIQUE),
						ConstraintViolations.REVIEWS_EMAIL_TITLE_ID_UNIQUE_MESSAGE));
	}

}

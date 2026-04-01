package com.example.critoria.api.advice;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.critoria.api.advice.exception.BadRequestException;
import com.example.critoria.api.advice.exception.NotFoundException;
import com.example.critoria.api.constant.ConstraintViolations;
import com.example.critoria.api.dto.ApiErrorResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiErrorResponseDTO.builder().message(ConstraintViolations.getMessage(ex)).build());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException(NotFoundException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleBadRequestException(BadRequestException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.badRequest().body(ApiErrorResponseDTO.builder().message(ex.getMessage()).build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));

		log.warn(message);

		return ResponseEntity.badRequest().body(ApiErrorResponseDTO.builder().message(message).build());
	}

}

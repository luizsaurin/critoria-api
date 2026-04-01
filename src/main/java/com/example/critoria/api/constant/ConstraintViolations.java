package com.example.critoria.api.constant;

import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ConstraintViolations {
	
	private ConstraintViolations() {
		throw new IllegalStateException("Utility class");
	}

	public static final String DEFAULT_MESSAGE = "Database constraint violation";

	public static final String TITLES_NAME_UNIQUE = "titles_name_unique";
	public static final String TITLES_NAME_UNIQUE_MESSAGE = "Title must have a unique name";
	public static final String TITLES_ID_FK = "title_id_fk";
	public static final String TITLES_ID_FK_MESSAGE = "Title has reviews associated with it";
	public static final String REVIEWS_EMAIL_TITLE_ID_UNIQUE = "reviews_email_title_id_unique";
	public static final String REVIEWS_EMAIL_TITLE_ID_UNIQUE_MESSAGE = "User already has reviewed this title";

	public static final Map<String, String> CONSTRAINT_MAP = Map.of(
        TITLES_NAME_UNIQUE, TITLES_NAME_UNIQUE_MESSAGE,
        TITLES_ID_FK, TITLES_ID_FK_MESSAGE,
        REVIEWS_EMAIL_TITLE_ID_UNIQUE, REVIEWS_EMAIL_TITLE_ID_UNIQUE_MESSAGE
    );

	public static final String getMessage(DataIntegrityViolationException ex) {
		try {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
			return CONSTRAINT_MAP.getOrDefault(constraintViolationException.getConstraintName(), DEFAULT_MESSAGE);
		} catch (ClassCastException | NullPointerException _) {
			log.warn("Could not get message for constraint");
			return DEFAULT_MESSAGE;
		}
	}
}

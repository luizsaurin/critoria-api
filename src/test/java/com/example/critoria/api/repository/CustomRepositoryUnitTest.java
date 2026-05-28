package com.example.critoria.api.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class CustomRepositoryUnitTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private CustomRepository customRepository;

	@Test
	void silentBulkInsertTitles_success() {

		when(jdbcTemplate.batchUpdate(anyString(), anyList(), anyInt(), any())).thenReturn(null);

		assertAll(() -> customRepository.silentBulkInsertTitles(new ArrayList<>()));
	}

	@Test
	void silentBulkInsertTitles_exception() {

		doThrow(new RuntimeException()).when(jdbcTemplate).batchUpdate(anyString(), anyList(), anyInt(), any());

		assertAll(() -> customRepository.silentBulkInsertTitles(new ArrayList<>()));
	}

}

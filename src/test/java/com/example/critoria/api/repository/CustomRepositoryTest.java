package com.example.critoria.api.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.util.TitleFixtures;

@ExtendWith(MockitoExtension.class)
class CustomRepositoryTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private CustomRepository customRepository;

	@Test
	void silentBulkInsertTitles_success() {

		List<CreateTitleRequestDTO> inputList = List.of(new CreateTitleRequestDTO());

		customRepository.silentBulkInsertTitles(inputList);

		verify(jdbcTemplate).batchUpdate(anyString(), anyList(), anyInt(), any());
	}

	@Test
	@SuppressWarnings("unchecked")
	void silentBulkInsertTitles_correctStatement() throws SQLException {

		CreateTitleRequestDTO inputDto = TitleFixtures.buildCreateTitleRequestDTO();

		List<CreateTitleRequestDTO> inputList = List.of(inputDto);

		ArgumentCaptor<ParameterizedPreparedStatementSetter<CreateTitleRequestDTO>> captor = ArgumentCaptor
				.forClass(ParameterizedPreparedStatementSetter.class);

		customRepository.silentBulkInsertTitles(inputList);

		verify(jdbcTemplate).batchUpdate(anyString(), eq(inputList), eq(inputList.size()), captor.capture());

		ParameterizedPreparedStatementSetter<CreateTitleRequestDTO> setter = captor.getValue();

		PreparedStatement ps = mock(PreparedStatement.class);

		setter.setValues(ps, inputList.get(0));

		verify(ps).setString(1, inputDto.getName());
		verify(ps).setInt(2, inputDto.getReleaseYear());
	}

	@Test
	void silentBulkInsertTitles_handleException() {
		List<CreateTitleRequestDTO> input = List.of(new CreateTitleRequestDTO());

		doThrow(new RuntimeException("DB error"))
				.when(jdbcTemplate)
				.batchUpdate(anyString(), anyList(), anyInt(), any());

		assertDoesNotThrow(() -> customRepository.silentBulkInsertTitles(input));

		verify(jdbcTemplate).batchUpdate(anyString(), anyList(), anyInt(), any());
	}
}

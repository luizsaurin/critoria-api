package com.example.critoria.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.critoria.api.constant.TestConstants;
import com.example.critoria.api.dto.CreateTitleRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles(TestConstants.TEST_PROFILE)
class CustomRepositoryIntegrationTest {

	@Autowired
	private CustomRepository customRepository;

	@Autowired
	private TitleRepository titleRepository;

	@ParameterizedTest
	@MethodSource("silentBulkInsertTitlesTestCases")
	void silentBulkInsertTitles(long expectedCount, List<CreateTitleRequestDTO> entities) {

		customRepository.silentBulkInsertTitles(entities);

		assertEquals(expectedCount, titleRepository.count());
	}

	static Stream<Arguments> silentBulkInsertTitlesTestCases() {
		return Stream.of(
				Arguments.of(
						2L,
						List.of(
								CreateTitleRequestDTO.builder().name("Title 1").releaseYear(2000).build(),
								CreateTitleRequestDTO.builder().name("Title 2").releaseYear(2000).build())),
				Arguments.of(
						1L,
						List.of(
								CreateTitleRequestDTO.builder().name("Title 1").releaseYear(2000).build(),
								CreateTitleRequestDTO.builder().name("Title 1").releaseYear(2000).build())),
				Arguments.of(
						2L,
						List.of(
								CreateTitleRequestDTO.builder().name("Title 1").releaseYear(2000).build(),
								CreateTitleRequestDTO.builder().name("Title 1").releaseYear(2000).build(),
								CreateTitleRequestDTO.builder().name("Title 2").releaseYear(2000).build())),
				Arguments.of(
					0L, new ArrayList<>()));
	}

}

package com.example.critoria.api.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.critoria.api.constant.TestConstants;
import com.example.critoria.api.entity.TitleEntity;
import com.example.critoria.api.repository.TitleRepository;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles(TestConstants.TEST_PROFILE)
class TitleSpecificationsTest {

	@Autowired
	private TitleRepository titleRepository;

	private TitleEntity entity1;
	private TitleEntity entity2;

	@BeforeEach
	void beforeEach() {
		entity1 = TitleEntity.builder().name("Star Wars: A New Hope").releaseYear(1977).build();
		entity2 = TitleEntity.builder().name("Star Wars: The Empire Strikes Back").releaseYear(1980).build();
		titleRepository.saveAll(List.of(entity1, entity2));
	}

	@Test
	void noSpecification_returnAll() {
		List<TitleEntity> result = titleRepository.findAll();

		assertEquals(2, result.size());
	}

	@Test
	void nameContains_nullName_returnAll() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.nameContains(null));
		
		assertEquals(2, result.size());
	}

	@Test
	void nameContains_commonName_returnMatches() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.nameContains("Star Wars"));
		
		assertEquals(2, result.size());
	}

	@Test
	void nameContains_oneValidName_returnOneMatch() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.nameContains("New Hope"));
		
		assertEquals(1, result.size());
		assertEquals(entity1, result.getFirst());
	}

	@Test
	void nameContains_nonExistingName_returnNone() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.nameContains("Example"));
		
		assertTrue(result.isEmpty());
	}

	@Test
	void releaseYearGte_nullYear_returnAll() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.releaseYearGte(null));
		
		assertEquals(2, result.size());
	}

	@Test
	void releaseYearGte_yearZero_returnMatches() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.releaseYearGte(0));
		
		assertEquals(2, result.size());
	}

	@Test
	void releaseYearGte_oneValidYear_returnMatch() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.releaseYearGte(1980));
		
		assertEquals(1, result.size());
	}

	@Test
	void releaseYearGte_hightValueYear_returnNone() {
		List<TitleEntity> result = titleRepository.findAll(TitleSpecifications.releaseYearGte(9999));
		
		assertTrue(result.isEmpty());
	}

}

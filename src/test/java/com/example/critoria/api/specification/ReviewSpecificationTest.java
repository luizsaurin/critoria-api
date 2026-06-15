package com.example.critoria.api.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.critoria.api.constant.TestConstants;
import com.example.critoria.api.entity.ReviewEntity;
import com.example.critoria.api.entity.TitleEntity;
import com.example.critoria.api.repository.ReviewRepository;
import com.example.critoria.api.repository.TitleRepository;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles(TestConstants.TEST_PROFILE)
class ReviewSpecificationTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private TitleRepository titleRepository;

	@BeforeEach
	void beforeEach() {
		TitleEntity title = titleRepository.save(TitleEntity.builder().name("Invincible").releaseYear(2021).build());

		reviewRepository.saveAll(List.of(
				ReviewEntity.builder()
						.email("john.doe@mail.com").rating(5).description("Amazing!").title(title)
						.build(),
				ReviewEntity.builder()
						.email("ana.smith@mail.com").rating(3).description("Too gory but thrilling story!").title(title)
						.build()

		));

	}

	@Test
	void noSpecification_returnAll() {
		List<ReviewEntity> result = reviewRepository.findAll();

		assertEquals(2, result.size());
	}
	
	@Test
	void titleIdEquals_nullTitleId_returnAll() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.titleIdEquals(null));
		
		assertEquals(2, result.size());
	}
	
	@Test
	void titleIdEquals_validTitleId_returnMatches() {
		Long titleId = titleRepository.findAll().getFirst().getId();
		
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.titleIdEquals(titleId));
		
		assertEquals(2, result.size());
	}
	
	@Test
	void titleIdEquals_nonExistingTitleId_returnNone() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.titleIdEquals(999L));
		
		assertEquals(0, result.size());
	}
	
	@Test
	void ratingGte_nullRating_returnAll() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingGte(null));
		
		assertEquals(2, result.size());
	}

	@Test
	void ratingGte_minRating_returnMatches() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingGte(1));
		
		assertEquals(2, result.size());
	}

	@Test
	void ratingGte_maxRating_returnMatches() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingGte(5));
		
		assertEquals(1, result.size());
	}

	@Test
	void ratingLte_nullRating_returnAll() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingLte(null));
		
		assertEquals(2, result.size());
	}

	@Test
	void ratingLte_maxRating_returnMatches() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingLte(5));
		
		assertEquals(2, result.size());
	}

	@Test
	void ratingGte_lowRating_returnMatches() {
		List<ReviewEntity> result = reviewRepository.findAll(ReviewSpecifications.ratingLte(3));
		
		assertEquals(1, result.size());
	}
}

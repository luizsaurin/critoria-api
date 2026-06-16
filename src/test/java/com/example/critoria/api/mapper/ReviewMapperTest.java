package com.example.critoria.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.entity.ReviewEntity;
import com.example.critoria.api.entity.TitleEntity;

class ReviewMapperTest {

	@Test
	void toEntity_CreateReviewRequestDTO_TitleEntity() {
		CreateReviewRequestDTO dto = CreateReviewRequestDTO.builder()
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();

		TitleEntity titleEntity = new TitleEntity();

		ReviewEntity expected = ReviewEntity.builder()
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		ReviewEntity result = ReviewMapper.INSTANCE.toEntity(dto, titleEntity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toEntity_ReviewEntity_UpdateReviewRequestDTO() {
		TitleEntity titleEntity = new TitleEntity();

		ReviewEntity reviewEntity = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		UpdateReviewRequestDTO dto = UpdateReviewRequestDTO.builder()
				.rating(4)
				.description("Cool")
				.build();

		ReviewEntity expected = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(4)
				.description("Cool")
				.title(titleEntity)
				.build();

		ReviewEntity result = ReviewMapper.INSTANCE.toEntity(reviewEntity, dto);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toCreateReviewResponseDTO_ReviewEntity() {
		TitleEntity titleEntity = TitleEntity.builder()
				.id(1L)
				.build();

		ReviewEntity reviewEntity = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		CreateReviewResponseDTO expected = CreateReviewResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();

		CreateReviewResponseDTO result = ReviewMapper.INSTANCE.toCreateReviewResponseDTO(reviewEntity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toFindReviewByIdResponseDTO_ReviewEntity() {
		TitleEntity titleEntity = TitleEntity.builder()
				.id(1L)
				.build();

		ReviewEntity reviewEntity = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		FindReviewByIdResponseDTO expected = FindReviewByIdResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();

		FindReviewByIdResponseDTO result = ReviewMapper.INSTANCE.toFindReviewByIdResponseDTO(reviewEntity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toFindAllReviewsResponseDTO_ReviewEntity() {
		TitleEntity titleEntity = TitleEntity.builder()
				.id(1L)
				.build();

		ReviewEntity reviewEntity = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		FindAllReviewsResponseDTO expected = FindAllReviewsResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();

		FindAllReviewsResponseDTO result = ReviewMapper.INSTANCE.toFindAllReviewsResponseDTO(reviewEntity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toUpdateReviewResponseDTO_ReviewEntity() {
		TitleEntity titleEntity = TitleEntity.builder()
				.id(1L)
				.build();

		ReviewEntity reviewEntity = ReviewEntity.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.title(titleEntity)
				.build();

		UpdateReviewResponseDTO expected = UpdateReviewResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();

		UpdateReviewResponseDTO result = ReviewMapper.INSTANCE.toUpdateReviewResponseDTO(reviewEntity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}
}

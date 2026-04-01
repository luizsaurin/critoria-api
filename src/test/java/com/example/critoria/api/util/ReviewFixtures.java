package com.example.critoria.api.util;

import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;

public class ReviewFixtures {

	public static CreateReviewRequestDTO buildCreateReviewRequestDTO() {
		return CreateReviewRequestDTO.builder()
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();
	}

	public static CreateReviewResponseDTO buildCreateReviewResponseDTO() {
		return CreateReviewResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();
	}

	public static FindReviewByIdResponseDTO buildFindReviewByIdResponseDTO() {
		return FindReviewByIdResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();
	}

	public static FindAllReviewsResponseDTO buildFindAllReviewsResponseDTO() {
		return FindAllReviewsResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();
	}

	public static UpdateReviewRequestDTO buildUpdateReviewRequestDTO() {
		return UpdateReviewRequestDTO.builder()
				.rating(5)
				.description("Awesome!")
				.build();
	}

	public static UpdateReviewResponseDTO buildUpdateReviewResponseDTO() {
		return UpdateReviewResponseDTO.builder()
				.id(1L)
				.email("john.doe@mail.com")
				.rating(5)
				.description("Awesome!")
				.titleId(1L)
				.build();
	}
}

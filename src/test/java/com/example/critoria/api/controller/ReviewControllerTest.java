package com.example.critoria.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.critoria.api.constant.URIs;
import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.service.ReviewService;
import com.example.critoria.api.util.ReviewFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ReviewService reviewService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void create_success() throws Exception {

		CreateReviewRequestDTO request = ReviewFixtures.buildCreateReviewRequestDTO();
		CreateReviewResponseDTO response = ReviewFixtures.buildCreateReviewResponseDTO();

		when(reviewService.create(request)).thenReturn(response);

		mockMvc.perform(post(URIs.CREATE_REVIEW)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		verify(reviewService).create(request);
	}

	@Test
	void findById_success() throws Exception {

		Long id = 1L;
		FindReviewByIdResponseDTO response = ReviewFixtures.buildFindReviewByIdResponseDTO();

		when(reviewService.findById(id)).thenReturn(response);

		mockMvc.perform(get(URIs.FIND_REVIEW_BY_ID, id))
				.andExpect(status().isOk());

		verify(reviewService).findById(id);
	}

	@Test
	void findAll_success() throws Exception {

		Long titleIdParam = 1L;
		Integer ratingGteParam = 4;
		Integer ratingLteParam = 5;
		Pageable pageableParam = Pageable.unpaged();

		Page<FindAllReviewsResponseDTO> responsePage = new PageImpl<>(
				List.of(ReviewFixtures.buildFindAllReviewsResponseDTO()));

		when(reviewService.findAll(pageableParam, titleIdParam, ratingGteParam, ratingLteParam))
				.thenReturn(responsePage);

		mockMvc.perform(get(URIs.FIND_REVIEWS))
				.andExpect(status().isOk());

		verify(reviewService).findAll(any(), any(), any(), any());
	}

	@Test
	void update_success() throws Exception {

		Long id = 1L;
		UpdateReviewRequestDTO request = ReviewFixtures.buildUpdateReviewRequestDTO();
		UpdateReviewResponseDTO response = ReviewFixtures.buildUpdateReviewResponseDTO();

		when(reviewService.update(id, request)).thenReturn(response);

		mockMvc.perform(put(URIs.UPDATE_REVIEW, id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());

		verify(reviewService).update(id, request);
	}

	@Test
	void delete_success() throws Exception {

		Long id = 1L;

		mockMvc.perform(delete(URIs.DELETE_REVIEW, id))
				.andExpect(status().isNoContent());

		verify(reviewService).delete(id);
	}

}

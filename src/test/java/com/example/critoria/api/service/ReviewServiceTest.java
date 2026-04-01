package com.example.critoria.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.critoria.api.advice.exception.BadRequestException;
import com.example.critoria.api.advice.exception.NotFoundException;
import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.entity.ReviewEntity;
import com.example.critoria.api.entity.TitleEntity;
import com.example.critoria.api.mapper.ReviewMapper;
import com.example.critoria.api.repository.ReviewRepository;
import com.example.critoria.api.repository.TitleRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@Mock
	private ReviewMapper reviewMapper;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private TitleRepository titleRepository;

	@InjectMocks
	private ReviewService reviewService;

	@Test
	void create_titleNotFound() {

		CreateReviewRequestDTO request = new CreateReviewRequestDTO();

		when(titleRepository.findById(request.getTitleId())).thenReturn(Optional.empty());

		assertThrows(BadRequestException.class, () -> reviewService.create(request));
		verify(titleRepository).findById(request.getTitleId());
	}

	@Test
	void create_success() {

		CreateReviewRequestDTO request = new CreateReviewRequestDTO();
		TitleEntity titleEntity = new TitleEntity();
		ReviewEntity reviewEntity = new ReviewEntity();
		CreateReviewResponseDTO response = new CreateReviewResponseDTO();

		when(titleRepository.findById(request.getTitleId())).thenReturn(Optional.of(titleEntity));
		when(reviewMapper.toEntity(request, titleEntity)).thenReturn(reviewEntity);
		when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
		when(reviewMapper.toCreateReviewResponseDTO(reviewEntity)).thenReturn(response);

		CreateReviewResponseDTO result = reviewService.create(request);

		assertEquals(response, result);
		verify(titleRepository).findById(request.getTitleId());
		verify(reviewMapper).toEntity(request, titleEntity);
		verify(reviewRepository).save(reviewEntity);
		verify(reviewMapper).toCreateReviewResponseDTO(reviewEntity);
	}

	@Test
	void findById_notFound() {

		Long id = 1L;

		when(reviewRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> reviewService.findById(id));
		verify(reviewRepository).findById(id);
	}

	@Test
	void findById_success() {

		Long id = 1L;
		ReviewEntity reviewEntity = new ReviewEntity();
		FindReviewByIdResponseDTO response = new FindReviewByIdResponseDTO();

		when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewEntity));
		when(reviewMapper.toFindReviewByIdResponseDTO(reviewEntity)).thenReturn(response);

		FindReviewByIdResponseDTO result = reviewService.findById(id);

		assertEquals(response, result);
	}

	@Test
	@SuppressWarnings("unchecked")
	void findAll_success() {

		Pageable pageable = Pageable.unpaged();
		ReviewEntity entity = new ReviewEntity();
		Page<ReviewEntity> entityPage = new PageImpl<>(List.of(entity));
		FindAllReviewsResponseDTO responseDto = new FindAllReviewsResponseDTO();
		Page<FindAllReviewsResponseDTO> responsePage = new PageImpl<>(List.of(responseDto));

		when(reviewRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(entityPage);
		when(reviewMapper.toFindAllReviewsResponseDTO(entity)).thenReturn(responseDto);

		Page<FindAllReviewsResponseDTO> result = reviewService.findAll(pageable, null, null, null);

		assertEquals(1, result.getTotalElements());
		assertEquals(responsePage.getContent().get(0), result.getContent().get(0));
		verify(reviewRepository).findAll(any(Specification.class), any(Pageable.class));
	}

	@Test
	void update_notFound() {

		Long id = 1L;
		UpdateReviewRequestDTO request = new UpdateReviewRequestDTO();

		when(reviewRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> reviewService.update(id, request));
		verify(reviewRepository).findById(id);
	}

	@Test
	void update_success() {

		Long id = 1L;
		ReviewEntity reviewEntity = new ReviewEntity();
		UpdateReviewRequestDTO request = new UpdateReviewRequestDTO();
		UpdateReviewResponseDTO response = new UpdateReviewResponseDTO();

		when(reviewRepository.findById(id)).thenReturn(Optional.of(reviewEntity));
		when(reviewMapper.toEntity(reviewEntity, request)).thenReturn(reviewEntity);
		when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
		when(reviewMapper.toUpdateReviewResponseDTO(reviewEntity)).thenReturn(response);

		UpdateReviewResponseDTO result = reviewService.update(id, request);

		assertEquals(response, result);
		verify(reviewRepository).findById(id);
		verify(reviewMapper).toEntity(reviewEntity, request);
		verify(reviewRepository).save(reviewEntity);
		verify(reviewMapper).toUpdateReviewResponseDTO(reviewEntity);
	}

	@Test
	void delete_success() {

		Long id = 1L;

		doNothing().when(reviewRepository).deleteById(id);

		reviewService.delete(id);

		verify(reviewRepository).deleteById(id);
	}

}

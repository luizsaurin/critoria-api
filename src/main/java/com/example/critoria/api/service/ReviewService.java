package com.example.critoria.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.critoria.api.advice.exception.BadRequestException;
import com.example.critoria.api.advice.exception.NotFoundException;
import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.entity.ReviewEntity;
import com.example.critoria.api.mapper.ReviewMapper;
import com.example.critoria.api.repository.ReviewRepository;
import com.example.critoria.api.repository.TitleRepository;
import com.example.critoria.api.specification.ReviewSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewMapper reviewMapper;
	private final ReviewRepository reviewRepository;
	private final TitleRepository titleRepository;

	public CreateReviewResponseDTO create(CreateReviewRequestDTO request) {
		log.info("Creating review with data {}", request);

		return reviewMapper.toCreateReviewResponseDTO(
				reviewRepository.save(
						reviewMapper.toEntity(
								request,
								titleRepository.findById(request.getTitleId())
										.orElseThrow(() -> new BadRequestException("Title with id [{}] not found",
												request.getTitleId())))));
	}

	public FindReviewByIdResponseDTO findById(Long id) {
		log.info("Searching review with id [{}]", id);

		return reviewMapper.toFindReviewByIdResponseDTO(reviewRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Review with id [{}] not found", id)));
	}

	public Page<FindAllReviewsResponseDTO> findAll(Pageable pageable, Long titleId, Integer ratingGte,
			Integer ratingLte) {
		log.info("Searching reviews with {} and params titleId [{}], ratingGte [{}], ratingLte [{}]",
				pageable, titleId, ratingGte, ratingLte);

		Specification<ReviewEntity> spec = Specification.allOf(
				ReviewSpecifications.titleIdEquals(titleId),
				ReviewSpecifications.ratingGte(ratingGte),
				ReviewSpecifications.ratingLte(ratingLte));

		Page<FindAllReviewsResponseDTO> page = reviewRepository.findAll(spec, pageable)
				.map(reviewMapper::toFindAllReviewsResponseDTO);

		log.info("[{}] records found", page.getTotalElements());

		return page;
	}

	public UpdateReviewResponseDTO update(Long id, UpdateReviewRequestDTO request) {
		log.info("Updating review with id [{}] using data {}", id, request);

		return reviewMapper.toUpdateReviewResponseDTO(
				reviewRepository.save(
						reviewMapper.toEntity(
								reviewRepository.findById(id)
										.orElseThrow(() -> new NotFoundException("Review with id [{}] not found", id)),
								request)));
	}

	public void delete(Long id) {
		log.info("Deleting review id [{}]", id);
		reviewRepository.deleteById(id);
	}
}

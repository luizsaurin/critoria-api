package com.example.critoria.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.critoria.api.constant.URIs;
import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping(URIs.CREATE_REVIEW)
	public ResponseEntity<CreateReviewResponseDTO> create(@RequestBody @Valid CreateReviewRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(request));
	}

	@GetMapping(URIs.FIND_REVIEW_BY_ID)
	public ResponseEntity<FindReviewByIdResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(reviewService.findById(id));
	}

	@GetMapping(URIs.FIND_REVIEWS)
	public ResponseEntity<Page<FindAllReviewsResponseDTO>> findAll(
		@RequestParam(required = false) Long titleId,
		@RequestParam(required = false) Integer ratingGte,
		@RequestParam(required = false) Integer ratingLte,
		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(reviewService.findAll(pageable, titleId, ratingGte, ratingLte));
	}

	@PutMapping(URIs.UPDATE_REVIEW)
	public ResponseEntity<UpdateReviewResponseDTO> update(
		@PathVariable Long id, @RequestBody UpdateReviewRequestDTO request) {
		return ResponseEntity.ok(reviewService.update(id, request));
	}

	@DeleteMapping(URIs.DELETE_REVIEW)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		reviewService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

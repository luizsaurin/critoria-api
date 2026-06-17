package com.example.critoria.api.interfaces;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Review", description = "Endpoints to manage reviews")
public interface ReviewControllerOpenApi {

	@Operation(summary = "Create review")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<CreateReviewResponseDTO> create(CreateReviewRequestDTO request);

	@Operation(summary = "Find review by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<FindReviewByIdResponseDTO> findById(Long id);

	@Operation(summary = "Find reviews with params and pagination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<Page<FindAllReviewsResponseDTO>> findAll(
			Long titleId, Integer ratingGte, Integer ratingLte, @ParameterObject Pageable pageable);

	@Operation(summary = "Update review")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<UpdateReviewResponseDTO> update(Long id, UpdateReviewRequestDTO request);

	@Operation(summary = "Delete review")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<Void> delete(Long id);
}

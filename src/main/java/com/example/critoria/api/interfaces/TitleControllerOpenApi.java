package com.example.critoria.api.interfaces;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Title", description = "Endpoints to manage titles")
public interface TitleControllerOpenApi {

	@Operation(summary = "Create title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<CreateTitleResponseDTO> create(CreateTitleRequestDTO request);

	@Operation(summary = "Create titles asynchronously")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Accepted"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<Void> createMany(List<CreateTitleRequestDTO> request);

	@Operation(summary = "Find title by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<FindTitleByIdResponseDTO> findById(Long id);

	@Operation(summary = "Find titles with params and pagination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<Page<FindAllTitlesResponseDTO>> findAll(
			String name, Integer releaseYearGte, Integer releaseYearLte, @ParameterObject Pageable pageable);

	@Operation(summary = "Update title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<UpdateTitleResponseDTO> update(Long id, UpdateTitleRequestDTO request);

	@Operation(summary = "Delete title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
	})
	ResponseEntity<Void> delete(Long id);
}

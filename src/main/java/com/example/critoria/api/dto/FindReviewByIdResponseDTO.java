package com.example.critoria.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindReviewByIdResponseDTO {
	
	private Long id;
	private String email;
	private Integer rating;
	private String description;
	private Long titleId;
}

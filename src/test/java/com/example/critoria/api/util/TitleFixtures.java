package com.example.critoria.api.util;

import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;

public class TitleFixtures {

	public static CreateTitleRequestDTO buildCreateTitleRequestDTO() {
		return CreateTitleRequestDTO.builder()
				.name("Star Wars")
				.releaseYear(1977)
				.build();
	}

	public static CreateTitleResponseDTO buildCreateTitleResponseDTO() {
		return CreateTitleResponseDTO.builder()
				.id(1L)
				.name("Star Wars")
				.releaseYear(1977)
				.build();
	}

	public static FindTitleByIdResponseDTO buildFindTitleByIdResponseDTO() {
		return FindTitleByIdResponseDTO.builder()
				.id(1L)
				.name("Star Wars")
				.releaseYear(1977)
				.build();
	}

	public static FindAllTitlesResponseDTO buildFindAllTitlesResponseDTO() {
		return FindAllTitlesResponseDTO.builder()
				.id(1L)
				.name("Star Wars")
				.releaseYear(1977)
				.build();
	}

	public static UpdateTitleRequestDTO buildUpdateTitleRequestDTO() {
		return UpdateTitleRequestDTO.builder()
				.name("Star Wars")
				.build();
	}

	public static UpdateTitleResponseDTO buildUpdateTitleResponseDTO() {
		return UpdateTitleResponseDTO.builder()
				.id(1L)
				.name("Star Wars")
				.build();
	}
}

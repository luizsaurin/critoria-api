package com.example.critoria.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;
import com.example.critoria.api.entity.TitleEntity;

class TitleMapperTest {

	@Test
	void toEntity_CreateTitleRequestDTO() {

		CreateTitleRequestDTO dto = CreateTitleRequestDTO.builder()
				.name("Title 1")
				.releaseYear(2000)
				.build();

		TitleEntity expected = TitleEntity.builder()
				.name("Title 1")
				.releaseYear(2000)
				.build();

		TitleEntity result = TitleMapper.INSTANCE.toEntity(dto);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toEntity_TitleEntity_UpdateTitleRequestDTO() {
		TitleEntity existingEntity = TitleEntity.builder()
				.name("Title 1")
				.releaseYear(2000)
				.build();

		UpdateTitleRequestDTO dto = UpdateTitleRequestDTO.builder()
				.name("Title 2")
				.releaseYear(2001)
				.build();

		TitleEntity expected = TitleEntity.builder()
				.name("Title 2")
				.releaseYear(2001)
				.build();

		TitleEntity result = TitleMapper.INSTANCE.toEntity(existingEntity, dto);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toCreateTitleResponseDTO_TitleEntity() {
		TitleEntity entity = TitleEntity.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		CreateTitleResponseDTO expected = CreateTitleResponseDTO.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		CreateTitleResponseDTO result = TitleMapper.INSTANCE.toCreateTitleResponseDTO(entity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toFindTitleByIdResponseDTO_TitleEntity() {
		TitleEntity entity = TitleEntity.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		FindTitleByIdResponseDTO expected = FindTitleByIdResponseDTO.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		FindTitleByIdResponseDTO result = TitleMapper.INSTANCE.toFindTitleByIdResponseDTO(entity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toFindAllTitlesResponseDTO_TitleEntity() {
		TitleEntity entity = TitleEntity.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		FindAllTitlesResponseDTO expected = FindAllTitlesResponseDTO.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		FindAllTitlesResponseDTO result = TitleMapper.INSTANCE.toFindAllTitlesResponseDTO(entity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}

	@Test
	void toUpdateTitleResponseDTO_TitleEntity() {
		TitleEntity entity = TitleEntity.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		UpdateTitleResponseDTO expected = UpdateTitleResponseDTO.builder()
				.id(1L)
				.name("Title 1")
				.releaseYear(2000)
				.build();

		UpdateTitleResponseDTO result = TitleMapper.INSTANCE.toUpdateTitleResponseDTO(entity);

		assertThat(result)
				.isNotNull()
				.usingRecursiveComparison()
				.isEqualTo(expected);
	}
}

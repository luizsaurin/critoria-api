package com.example.critoria.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;
import com.example.critoria.api.entity.TitleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleMapper {

	TitleEntity toEntity(CreateTitleRequestDTO dto);

	TitleEntity toEntity(@MappingTarget TitleEntity entity, UpdateTitleRequestDTO dto);

	CreateTitleResponseDTO toCreateTitleResponseDTO(TitleEntity entity);

	FindTitleByIdResponseDTO toFindTitleByIdResponseDTO(TitleEntity entity);

	FindAllTitlesResponseDTO toFindAllTitlesResponseDTO(TitleEntity entity);
	
	UpdateTitleResponseDTO toUpdateTitleResponseDTO(TitleEntity entity);
}

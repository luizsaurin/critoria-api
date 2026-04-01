package com.example.critoria.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.critoria.api.dto.CreateReviewRequestDTO;
import com.example.critoria.api.dto.CreateReviewResponseDTO;
import com.example.critoria.api.dto.FindAllReviewsResponseDTO;
import com.example.critoria.api.dto.FindReviewByIdResponseDTO;
import com.example.critoria.api.dto.UpdateReviewRequestDTO;
import com.example.critoria.api.dto.UpdateReviewResponseDTO;
import com.example.critoria.api.entity.ReviewEntity;
import com.example.critoria.api.entity.TitleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "titleEntity", target = "title")
	ReviewEntity toEntity(CreateReviewRequestDTO dto, TitleEntity titleEntity);
	
	ReviewEntity toEntity(@MappingTarget ReviewEntity reviewEntity, UpdateReviewRequestDTO dto);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	CreateReviewResponseDTO toCreateReviewResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	FindReviewByIdResponseDTO toFindReviewByIdResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	FindAllReviewsResponseDTO toFindAllReviewsResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	UpdateReviewResponseDTO toUpdateReviewResponseDTO(ReviewEntity entity);
}

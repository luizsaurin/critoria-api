package com.example.critoria.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.critoria.api.advice.exception.NotFoundException;
import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;
import com.example.critoria.api.entity.TitleEntity;
import com.example.critoria.api.mapper.TitleMapper;
import com.example.critoria.api.repository.CustomRepository;
import com.example.critoria.api.repository.TitleRepository;
import com.example.critoria.api.specification.TitleSpecifications;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TitleService {

	private final CustomRepository customRepository;
	private final TitleRepository titleRepository;
	private final TitleMapper titleMapper;
	private final Validator validator;

	public CreateTitleResponseDTO create(CreateTitleRequestDTO request) {
		log.info("Creating title with data: {}", request);
		return titleMapper.toCreateTitleResponseDTO(titleRepository.save(titleMapper.toEntity(request)));
	}

	@Async
	public void createMany(List<CreateTitleRequestDTO> requestList) {
		log.info("Creating titles with data: {}", requestList);

		if (requestList == null || requestList.isEmpty()) {
			log.warn("Request list is null or empty");
			return;
		}

		List<CreateTitleRequestDTO> validatedList = requestList.stream()
				.filter(r -> r != null && validator.validate(r).isEmpty())
				.toList();

		customRepository.silentBulkInsertTitles(validatedList);
	}

	public FindTitleByIdResponseDTO findById(Long id) {
		log.info("Searching title with id [{}]", id);
		return titleMapper.toFindTitleByIdResponseDTO(
				titleRepository.findById(id)
						.orElseThrow(() -> new NotFoundException("Title with id [{}] not found", id)));
	}

	public Page<FindAllTitlesResponseDTO> findAll(Pageable pageable, String name, Integer releaseYearGte,
			Integer releaseYearLte) {
		log.info("Searching titles with {} and params name [{}], releaseYearGte [{}], releaseYearLte [{}]",
				pageable, name, releaseYearGte, releaseYearLte);

		Specification<TitleEntity> spec = Specification.allOf(
				TitleSpecifications.nameContains(name),
				TitleSpecifications.releaseYearGte(releaseYearGte),
				TitleSpecifications.releaseYearLte(releaseYearLte));

		Page<FindAllTitlesResponseDTO> page = titleRepository.findAll(spec, pageable)
				.map(titleMapper::toFindAllTitlesResponseDTO);

		log.info("[{}] records found", page.getTotalElements());

		return page;
	}

	public UpdateTitleResponseDTO update(Long id, UpdateTitleRequestDTO request) {
		log.info("Updating title with id [{}] using data {}", id, request);

		return titleMapper.toUpdateTitleResponseDTO(
				titleRepository.save(
						titleMapper.toEntity(
								titleRepository.findById(id)
										.orElseThrow(() -> new NotFoundException("Title with id [{}] not found", id)),
								request)));
	}

	public void delete(Long id) {
		log.info("Deleting title id [{}]", id);
		titleRepository.deleteById(id);
	}

}

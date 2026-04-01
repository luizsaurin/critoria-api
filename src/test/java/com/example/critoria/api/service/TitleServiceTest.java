package com.example.critoria.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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
import com.example.critoria.api.util.TitleFixtures;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
class TitleServiceTest {

	@Mock
	private CustomRepository customRepository;

	@Mock
	private TitleRepository titleRepository;

	@Mock
	private TitleMapper titleMapper;

	@Mock
	private Validator validator;

	@InjectMocks
	private TitleService titleService;

	@Test
	void create_success() {

		CreateTitleRequestDTO request = new CreateTitleRequestDTO();
		CreateTitleResponseDTO response = new CreateTitleResponseDTO();
		TitleEntity titleEntity = new TitleEntity();

		when(titleMapper.toEntity(request)).thenReturn(titleEntity);
		when(titleRepository.save(titleEntity)).thenReturn(titleEntity);
		when(titleMapper.toCreateTitleResponseDTO(titleEntity)).thenReturn(response);

		CreateTitleResponseDTO result = titleService.create(request);

		assertEquals(response, result);
		verify(titleMapper).toEntity(request);
		verify(titleRepository).save(titleEntity);
		verify(titleMapper).toCreateTitleResponseDTO(titleEntity);
	}

	@Test
	void createMany_nullInputList() {
		titleService.createMany(null);
		verifyNoInteractions(customRepository);
	}

	@Test
	void createMany_emptyInputList() {
		titleService.createMany(new ArrayList<>());
		verifyNoInteractions(customRepository);
	}

	@Test
	@SuppressWarnings("unchecked")
	void createMany_success() {
		CreateTitleRequestDTO valid = TitleFixtures.buildCreateTitleRequestDTO();
		CreateTitleRequestDTO invalid = new CreateTitleRequestDTO();

		List<CreateTitleRequestDTO> requestList = new ArrayList<>();
		requestList.add(null);
		requestList.add(valid);
		requestList.add(invalid);

		when(validator.validate(valid)).thenReturn(Set.of());
		when(validator.validate(invalid)).thenReturn(Set.of(mock(ConstraintViolation.class)));

		titleService.createMany(requestList);

		verify(customRepository).silentBulkInsertTitles(List.of(valid));
	}

	@Test
	void findById_notFound() {

		Long id = 1L;

		when(titleRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> titleService.findById(id));
		verify(titleRepository).findById(id);
	}

	@Test
	void findById_success() {

		Long id = 1L;
		TitleEntity titleEntity = new TitleEntity();
		FindTitleByIdResponseDTO response = new FindTitleByIdResponseDTO();

		when(titleRepository.findById(id)).thenReturn(Optional.of(titleEntity));
		when(titleMapper.toFindTitleByIdResponseDTO(titleEntity)).thenReturn(response);

		FindTitleByIdResponseDTO result = titleService.findById(id);

		assertEquals(response, result);
	}

	@Test
	@SuppressWarnings("unchecked")
	void findAll_success() {

		Pageable pageable = Pageable.unpaged();
		TitleEntity entity = new TitleEntity();
		Page<TitleEntity> entityPage = new PageImpl<>(List.of(entity));
		FindAllTitlesResponseDTO responseDto = new FindAllTitlesResponseDTO();
		Page<FindAllTitlesResponseDTO> responsePage = new PageImpl<>(List.of(responseDto));

		when(titleRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(entityPage);
		when(titleMapper.toFindAllTitlesResponseDTO(entity)).thenReturn(responseDto);

		Page<FindAllTitlesResponseDTO> result = titleService.findAll(pageable, null, null, null);

		assertEquals(1, result.getTotalElements());
		assertEquals(responsePage.getContent().get(0), result.getContent().get(0));
		verify(titleRepository).findAll(any(Specification.class), any(Pageable.class));
	}

	@Test
	void update_notFound() {

		Long id = 1L;
		UpdateTitleRequestDTO request = new UpdateTitleRequestDTO();

		when(titleRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> titleService.update(id, request));
		verify(titleRepository).findById(id);
	}

	@Test
	void update_success() {

		Long id = 1L;
		TitleEntity titleEntity = new TitleEntity();
		UpdateTitleRequestDTO request = new UpdateTitleRequestDTO();
		UpdateTitleResponseDTO response = new UpdateTitleResponseDTO();

		when(titleRepository.findById(id)).thenReturn(Optional.of(titleEntity));
		when(titleMapper.toEntity(titleEntity, request)).thenReturn(titleEntity);
		when(titleRepository.save(titleEntity)).thenReturn(titleEntity);
		when(titleMapper.toUpdateTitleResponseDTO(titleEntity)).thenReturn(response);

		UpdateTitleResponseDTO result = titleService.update(id, request);

		assertEquals(response, result);
		verify(titleRepository).findById(id);
		verify(titleMapper).toEntity(titleEntity, request);
		verify(titleRepository).save(titleEntity);
		verify(titleMapper).toUpdateTitleResponseDTO(titleEntity);
	}

	@Test
	void delete_success() {

		Long id = 1L;

		doNothing().when(titleRepository).deleteById(id);

		titleService.delete(id);

		verify(titleRepository).deleteById(id);
	}

}

package com.example.critoria.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.critoria.api.constant.URIs;
import com.example.critoria.api.dto.CreateTitleRequestDTO;
import com.example.critoria.api.dto.CreateTitleResponseDTO;
import com.example.critoria.api.dto.FindAllTitlesResponseDTO;
import com.example.critoria.api.dto.FindTitleByIdResponseDTO;
import com.example.critoria.api.dto.UpdateTitleRequestDTO;
import com.example.critoria.api.dto.UpdateTitleResponseDTO;
import com.example.critoria.api.service.TitleService;
import com.example.critoria.api.util.TitleFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TitleController.class)
class TitleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TitleService titleService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void create_success() throws Exception {

		CreateTitleRequestDTO request = TitleFixtures.buildCreateTitleRequestDTO();
		CreateTitleResponseDTO response = TitleFixtures.buildCreateTitleResponseDTO();

		when(titleService.create(request)).thenReturn(response);

		mockMvc.perform(post(URIs.CREATE_TITLE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		verify(titleService).create(request);
	}

	@Test
	void createMany_success() throws Exception {

		List<CreateTitleRequestDTO> request = List.of(TitleFixtures.buildCreateTitleRequestDTO());

		doNothing().when(titleService).createMany(request);

		mockMvc.perform(post(URIs.CREATE_TITLES)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isAccepted());

		verify(titleService).createMany(request);
	}

	@Test
	void findById_success() throws Exception {

		Long id = 1L;
		FindTitleByIdResponseDTO response = TitleFixtures.buildFindTitleByIdResponseDTO();

		when(titleService.findById(id)).thenReturn(response);

		mockMvc.perform(get(URIs.FIND_TITLE_BY_ID, id))
				.andExpect(status().isOk());

		verify(titleService).findById(id);
	}

	@Test
	void findAll_success() throws Exception {

		String nameParam = "Star Wars";
		Integer releaseYearGteParam = 4;
		Integer releaseYearLteParam = 5;
		Pageable pageableParam = Pageable.unpaged();

		Page<FindAllTitlesResponseDTO> responsePage = new PageImpl<>(
				List.of(TitleFixtures.buildFindAllTitlesResponseDTO()));

		when(titleService.findAll(pageableParam, nameParam, releaseYearGteParam, releaseYearLteParam))
				.thenReturn(responsePage);

		mockMvc.perform(get(URIs.FIND_TITLES))
				.andExpect(status().isOk());

		verify(titleService).findAll(any(), any(), any(), any());
	}

	@Test
	void update_success() throws Exception {

		Long id = 1L;
		UpdateTitleRequestDTO request = TitleFixtures.buildUpdateTitleRequestDTO();
		UpdateTitleResponseDTO response = TitleFixtures.buildUpdateTitleResponseDTO();

		when(titleService.update(id, request)).thenReturn(response);

		mockMvc.perform(put(URIs.UPDATE_TITLE, id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());

		verify(titleService).update(id, request);
	}

	@Test
	void delete_success() throws Exception {

		Long id = 1L;

		mockMvc.perform(delete(URIs.DELETE_TITLE, id))
				.andExpect(status().isNoContent());

		verify(titleService).delete(id);
	}

}

package com.example.critoria.api.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.critoria.api.dto.CreateTitleRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomRepository {

	private final JdbcTemplate jdbcTemplate;

	public void silentBulkInsertTitles(List<CreateTitleRequestDTO> inputList) {

		log.info("Inserting titles {}", inputList);

		try {
			String sql = "INSERT INTO titles (name, release_year) VALUES (?, ?) ON CONFLICT (name) DO NOTHING";

			jdbcTemplate.batchUpdate(sql, inputList, inputList.size(),
					(ps, entity) -> {
						ps.setString(1, entity.getName());
						ps.setInt(2, entity.getReleaseYear());
					});
		} catch (Exception e) {
			log.error("Error inserting titles", e);
		}
	}

}

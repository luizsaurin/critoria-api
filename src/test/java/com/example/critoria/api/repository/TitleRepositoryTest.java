package com.example.critoria.api.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.example.critoria.api.constant.TestConstants;
import com.example.critoria.api.entity.TitleEntity;

@DataJpaTest
@ActiveProfiles(TestConstants.TEST_PROFILE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TitleRepositoryTest {
	
	@Autowired
	private TitleRepository titleRepository;

	@Test
	void findById() {

		Optional<TitleEntity> title = titleRepository.findById(1L);
		
		assertTrue(title.isEmpty());
	}
}

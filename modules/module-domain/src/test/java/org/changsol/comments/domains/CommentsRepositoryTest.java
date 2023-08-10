package org.changsol.comments.domains;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CommentsRepositoryTest {
	@Autowired
	private CommentsRepository commentsRepository;

	@Test
	@DisplayName("벨류 객체 findAll() join test")
	void saveMember() {
		// given
		// when
		commentsRepository.findAll();
		// then
	}
}
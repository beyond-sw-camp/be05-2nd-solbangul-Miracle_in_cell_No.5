package com.solbangul.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.User;

@Transactional
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("없는 id를 find 했을 때 NoSuchException 발생 테스트")
	void findByIdNoSuchError() {
		assertThatThrownBy(() -> userRepository.findById(1L).get())
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("user 저장")
	void saveAndFindById() {
		User user = createUser();
		userRepository.save(user);

		User findUser = userRepository.findById(user.getId()).get();
		assertThat(findUser).isEqualTo(user); // JPA 동일성 보장
	}

	@Test
	@DisplayName("login id 유니크 제약 조건")
	void loginIdUnique() {
		User user1 = createUser();
		userRepository.save(user1);

		User user2 = createUser();

		assertThatThrownBy(() -> userRepository.save(user2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	private static User createUser() {
		User user = User.builder()
			.name("박상철")
			.loginId("아이디")
			.password("1234")
			.nickname("park")
			.gitEmail("test@github.com")
			.role(Role.USER)
			.build();
		return user;
	}
}
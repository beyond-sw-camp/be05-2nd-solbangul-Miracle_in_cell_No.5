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
	@DisplayName("없는 id를 find 했을 때 NoSuchException 발생")
	void findByIdNoSuchError() {
		assertThatThrownBy(() -> userRepository.findById(1L).get())
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("user 저장")
	void saveAndFindById() {
		User user = createUser("박상철", "아이디", "1234", "test@github.com", Role.ROLE_USER);
		userRepository.save(user);

		User findUser = userRepository.findById(user.getId()).get();
		assertThat(findUser).isEqualTo(user); // JPA 동일성 보장
	}

	@Test
	@DisplayName("login id 유니크 제약 조건")
	void loginIdUnique() {
		String uniqueLoginId = "동일한 아이디";
		User user1 = createUser("테스트1", uniqueLoginId, "1234", "test1@github.com", Role.ROLE_USER);
		userRepository.save(user1);

		User user2 = createUser("테스트2", uniqueLoginId, "12345", "test2@github.com", Role.ROLE_USER);

		assertThatThrownBy(() -> userRepository.save(user2))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("BaseTimeEntity 등록")
	void baseTimeEntity() {

	}

	private static User createUser(String name, String loginId, String password, String mail, Role role) {
		User user = User.builder()
			.name(name)
			.loginId(loginId)
			.password(password)
			.nickname("park")
			.gitEmail(mail)
			.role(role)
			.build();
		return user;
	}
}
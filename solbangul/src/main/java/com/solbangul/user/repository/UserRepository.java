package com.solbangul.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solbangul.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLoginId(String loginId);

	User findByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	boolean existsByGitEmail(String email);
}

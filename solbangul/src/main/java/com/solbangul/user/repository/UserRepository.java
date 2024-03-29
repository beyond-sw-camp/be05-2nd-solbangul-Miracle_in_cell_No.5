package com.solbangul.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.solbangul.user.domain.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLoginId(String loginId);

	User findByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	boolean existsByGitEmail(String email);

	@Query("UPDATE User u SET u.solbangul = u.solbangul + 1 WHERE u.id = :userId")
	void updateBySolbangul(@Param("userId") Long userId);

	User findByGitEmail(String gitEmail);

	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	void updatePassword(@Param("id") Long id, @Param("password") String password);
}

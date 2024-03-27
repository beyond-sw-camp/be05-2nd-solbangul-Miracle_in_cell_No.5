package com.solbangul.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.solbangul.user.domain.User;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByLoginId(String loginId);

	User findByLoginId(String loginId);

	boolean existsByNickname(String nickname);

	boolean existsByGitEmail(String email);

	@Query("UPDATE User SET solbangul= solbangul+1 WHERE id = :userId")
	void updateBySolbangul(@Param("userId") Long userId);

	User findByNickname(String nickname);

}

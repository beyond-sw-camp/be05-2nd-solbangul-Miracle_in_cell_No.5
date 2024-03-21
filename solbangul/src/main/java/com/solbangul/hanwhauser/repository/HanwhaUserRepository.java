package com.solbangul.hanwhauser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solbangul.hanwhauser.domain.HanwhaUser;

public interface HanwhaUserRepository extends JpaRepository<HanwhaUser, Long> {

	boolean existsHanwhaUserByGitEmail(String gitEmail);

	HanwhaUser findHanwhaUserByGitEmail(String gitEmail);
}

package com.solbangul.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	// 사용자가 로그인을 하면 스프링 시큐리티 config가 검증을 위해서 username을 넣어주는데,
	// username을 가지고 db의 username을 조회
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User loginUser = userRepository.findByLoginId(username);

		if (loginUser == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}
		return new CustomUserDetails(loginUser);
	}
}

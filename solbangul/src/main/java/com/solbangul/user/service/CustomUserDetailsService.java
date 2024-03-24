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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User loginUser = userRepository.findByLoginId(username);

		if (loginUser == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}
		return new CustomUserDetails(loginUser);
	}
}

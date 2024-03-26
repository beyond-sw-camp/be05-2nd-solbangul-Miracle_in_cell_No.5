package com.solbangul.mypage.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.mypage.domain.UpdateUserDto;
import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public User getMypageInfo(String username) {
		return userRepository.findByLoginId(username);
	}

	@Transactional
	public void updateUserInfo(UpdateUserDto updateUserDto, String loginId) {
		User user = userRepository.findByLoginId(loginId);
		if (user != null) {
			if (updateUserDto.getNickname() != null && !updateUserDto.getNickname().isEmpty()) {
				user.setNickname(updateUserDto.getNickname());
			}
			// if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
			// 	user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
			// }
			// 프로필 사진 변경 로직---> 이거잘 모르겠움...
			// if (updateUserDto.getProfileImage() != null && !updateUserDto.getProfileImage().isEmpty()) {
			//
			// }
		}
	}

	public List<Post> getMyPosts(String writer) {
		return postRepository.findAllByWriter(writer);
	}
}
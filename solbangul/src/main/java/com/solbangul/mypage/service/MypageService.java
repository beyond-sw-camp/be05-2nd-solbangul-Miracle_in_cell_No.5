package com.solbangul.mypage.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.solbangul.file.FileStore;
import com.solbangul.file.UploadFile;
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
	private final FileStore fileStore;

	@Transactional(readOnly = true)
	public User getMypageInfo(String username) {
		return userRepository.findByLoginId(username);
	}

	@Transactional
public void updateProfilePicture(MultipartFile multipartFile, String loginId) {
    User user = userRepository.findByLoginId(loginId);
    if (!multipartFile.isEmpty()) {
        UploadFile uploadFile = fileStore.storeFile(multipartFile);
        user.setProfileImage(uploadFile.getStoreFilename());
    }
}

@Transactional
public void updateNickname(String nickname, String loginId) {
    User user = userRepository.findByLoginId(loginId);
    user.setNickname(nickname);
}

@Transactional
public void updatePassword(String password, String loginId) {
    User user = userRepository.findByLoginId(loginId);
    user.setPassword(passwordEncoder.encode(password));
}



	public List<Post> getMyPosts(String writer) {
		return postRepository.findAllByWriter(writer);
	}
}
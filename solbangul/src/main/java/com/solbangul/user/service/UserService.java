package com.solbangul.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.file.FileStore;
import com.solbangul.file.UploadFile;
import com.solbangul.room.domain.Room;
import com.solbangul.room.repository.RoomRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.domain.dto.PasswordResetUserDto;
import com.solbangul.user.mail.dto.EmailRequestDto;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoomRepository roomRepository;
	private final FileStore fileStore;

	public User findOne(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	public boolean isExistsByLoginId(JoinRequestUserDto joinRequestUserDto) {
		return userRepository.existsByLoginId(joinRequestUserDto.getLoginId());
	}

	public boolean isExistsByNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}

	public boolean isEmailAlreadyExists(EmailRequestDto email) {
		return userRepository.existsByGitEmail(email.getEmail());
	}

	public PasswordResetUserDto findUserByEmail(String email) {
		User findUser = userRepository.findByGitEmail(email);
		return new PasswordResetUserDto(findUser);
	}

	@Transactional
	public void updatePassword(Long id, String resetPassword) {
		String encodeResetPassword = passwordEncoder.encode(resetPassword);
		userRepository.updatePassword(id, encodeResetPassword);
		log.info("user id={} 의 비밀번호가 변경되었습니다.", id);
	}

	@Transactional
	public Long join(JoinRequestUserDto joinRequestUserDto) {
		setEncodePassword(joinRequestUserDto);
		setProfileImage(joinRequestUserDto);

		User user = joinRequestUserDto.toEntity();

		// 회원가입 시, room 자동으로 생성
		Room room = createRoom(joinRequestUserDto, user);
		roomRepository.save(room);

		user.addRoom(room);
		return userRepository.save(user).getId();
	}

	private void setEncodePassword(JoinRequestUserDto joinRequestUserDto) {
		String encodePassword = passwordEncoder.encode(joinRequestUserDto.getPassword());
		joinRequestUserDto.setEncodedPassword(encodePassword);
	}

	private void setProfileImage(JoinRequestUserDto joinRequestUserDto) {
		UploadFile uploadFile = fileStore.storeFile(joinRequestUserDto.getMultipartFile());
		if (uploadFile != null) {
			joinRequestUserDto.setProfileImage(uploadFile.getStoreFilename());
		} else {
			joinRequestUserDto.setProfileImage("basic.png");
		}
	}

	private Room createRoom(JoinRequestUserDto joinRequestUserDto, User user) {
		return new Room(user, "안녕하세요, " + joinRequestUserDto.getName() + "의 방 입니다!",
			joinRequestUserDto.getName() + "의 방");
	}

	public User findByNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}
}

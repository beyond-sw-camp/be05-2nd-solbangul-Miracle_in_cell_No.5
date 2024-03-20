package com.solbangul.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.room.domain.Room;
import com.solbangul.room.repository.RoomRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoomRepository roomRepository;

	public User findOne(Long id) {
		return userRepository.findById(id).get();
	}

	@Transactional
	public void updateUser(Long id, String name) {
		User findUser = userRepository.findById(id).orElseThrow();
		findUser.setName(name);
	}

	@Transactional
	public Long join(JoinRequestUserDto joinRequestUserDto) {
		log.info("회원가입");

		String encodePassword = passwordEncoder.encode(joinRequestUserDto.getPassword());
		joinRequestUserDto.setEncodedPassword(encodePassword);
		User user = joinRequestUserDto.toEntity();

		// 회원가입 시, room 자동으로 생성 ! ( room save -> user에 room 넣고 -> user save
		Room room = new Room(user, "", "cw");
		roomRepository.save(room);
		user.setRoom(room);

		return userRepository.save(user).getId();
	}

	public boolean isExistsByLoginId(JoinRequestUserDto joinRequestUserDto) {
		return userRepository.existsByLoginId(joinRequestUserDto.getLoginId());
	}

	public boolean isExistsByNickname(JoinRequestUserDto joinRequestUserDto) {
		return userRepository.existsByNickname(joinRequestUserDto.getNickname());
	}
}

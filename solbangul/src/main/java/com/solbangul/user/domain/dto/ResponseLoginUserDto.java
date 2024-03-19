package com.solbangul.user.domain.dto;

import java.time.LocalDateTime;

import com.solbangul.room.domain.Room;
import com.solbangul.user.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseLoginUserDto {

	private String loginId;
	private Room room;
	private String password;
	private String name;
	private String nickname;
	private String gitEmail;
	private Role role;
	private String profilePictureUrl;
	private Integer solbangul;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@Builder
	public ResponseLoginUserDto(String loginId, Room room, String password, String name, String nickname,
		String gitEmail,
		Role role, String profilePictureUrl, Integer solbangul, LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.loginId = loginId;
		this.room = room;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gitEmail = gitEmail;
		this.role = role;
		this.profilePictureUrl = profilePictureUrl;
		this.solbangul = solbangul;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
}

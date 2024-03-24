package com.solbangul.user.domain.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.solbangul.room.domain.Room;
import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.User;

import lombok.Getter;

@Getter
public class AuthenticatedUserDto {

	private String loginId;
	private Room room;
	private String password;
	private String name;
	private String nickname;
	private String gitEmail;
	private Role role;
	private String profilePictureUrl;
	private Integer solbangul;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createdDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime modifiedDate;

	public AuthenticatedUserDto(User user) {
		this.loginId = user.getLoginId();
		this.room = user.getRoom();
		this.password = user.getPassword();
		this.name = user.getName();
		this.nickname = user.getNickname();
		this.gitEmail = user.getGitEmail();
		this.role = user.getRole();
		this.profilePictureUrl = user.getProfileImage();
		this.solbangul = user.getSolbangul();
		this.createdDate = user.getCreatedDate();
		this.modifiedDate = user.getModifiedDate();
	}
}

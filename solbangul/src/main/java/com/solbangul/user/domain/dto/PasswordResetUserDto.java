package com.solbangul.user.domain.dto;

import com.solbangul.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetUserDto {

	private Long id;
	private String username;
	private String userEmail;

	public PasswordResetUserDto(User user) {
		this.id = user.getId();
		this.username = user.getName();
		this.userEmail = user.getGitEmail();
	}
}

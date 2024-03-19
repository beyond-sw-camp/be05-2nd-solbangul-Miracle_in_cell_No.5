package com.solbangul.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class JoinUserDto {

	@NotBlank
	private String loginId;

	@Setter
	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;

	private Role role;

	public JoinUserDto(String loginId, String password, Role role) {
		this.loginId = loginId;
		this.password = password;
		this.role = role;
		this.name = "test";
		this.email = "test@gmail.com";
	}

	public User toEntity() {
		return new User(loginId, password, role);
	}
}

package com.solbangul.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginUserDto {

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;
}

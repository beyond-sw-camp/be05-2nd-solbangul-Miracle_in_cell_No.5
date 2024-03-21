package com.solbangul.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinRequestUserDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String loginId;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
		message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
	private String password;

	@NotBlank(message = "비밀번호 확인을 입력해주세요")
	private String passwordConfirm;

	@NotBlank(message = "이름을 입력해주세요")
	private String name;

	@NotBlank(message = "닉네임을 입력해주세요")
	private String nickname;

	@Email
	@NotBlank(message = "이메일을 입력해주세요")
	private String email;

	public void setEncodedPassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	public User toEntity() {
		return User.builder()
			.loginId(loginId)
			.password(password)
			.name(name)
			.gitEmail(email)
			.nickname(nickname)
			.role(Role.ROLE_USER)
			.build();
	}
}

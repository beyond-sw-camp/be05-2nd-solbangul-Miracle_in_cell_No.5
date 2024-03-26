package com.solbangul.mypage.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
	private String profileImage;
	private String nickname;
	private String password;
}

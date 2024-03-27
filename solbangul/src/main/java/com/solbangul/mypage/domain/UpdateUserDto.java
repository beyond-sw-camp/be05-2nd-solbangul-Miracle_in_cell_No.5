package com.solbangul.mypage.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
	private String profileImage;
	private String nickname;
	private String password;

	private MultipartFile multipartFile;
}

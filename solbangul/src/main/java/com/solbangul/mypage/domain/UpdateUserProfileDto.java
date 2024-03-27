package com.solbangul.mypage.domain;

import org.springframework.web.multipart.MultipartFile;

import com.solbangul.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileDto {
	private Long id;
	private String profileImage;
	private MultipartFile multipartFile;

	public UpdateUserProfileDto(User user) {
		this.id = user.getId();
		this.profileImage = user.getProfileImage();
	}
}

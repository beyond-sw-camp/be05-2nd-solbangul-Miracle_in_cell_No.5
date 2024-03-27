package com.solbangul.post.domain.dto;

import com.solbangul.post.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchListResponseDto {
	private Long id;

	private String title;

	public PostSearchListResponseDto(Post p) {
		this.id = p.getId();
		this.title = p.getTitle();
	}

}

package com.solbangul.post.domain.dto;

import com.solbangul.post.domain.Category;
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

	private Category category;

	public PostSearchListResponseDto(Post p) {
		this.id = p.getId();
		this.title = p.getTitle();
		this.category = getCategory();
	}

}

package com.solbangul.post.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostEditRequestDto {
	private String title;

	private Boolean publicYn;

	private Boolean annonyYn;

	private String content;

	@Enumerated(EnumType.STRING)
	private Category category;

	public PostEditRequestDto(Post p) {
		this.title = p.getTitle();
		this.publicYn = p.getPublicYn();
		this.annonyYn = p.getAnnonyYn();
		this.content = p.getContent();
		this.category = p.getCategory();
	}

	public Post toEntity() {
		return Post.builder()
			.title(title)
			.publicYn(publicYn)
			.annonyYn(annonyYn)
			.content(content)
			.category(category)
			.build();
	}
}

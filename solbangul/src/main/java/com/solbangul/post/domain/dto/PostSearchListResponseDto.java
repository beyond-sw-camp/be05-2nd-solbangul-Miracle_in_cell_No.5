package com.solbangul.post.domain.dto;

import java.time.LocalDateTime;

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
	private Boolean annonyYn;
	private String writer;
	private LocalDateTime createDate;

	public PostSearchListResponseDto(Post p) {
		this.id = p.getId();
		this.title = p.getTitle();
		this.category = p.getCategory();
		this.annonyYn = p.getAnnonyYn();
		this.writer = p.getWriter();
		this.createDate = p.getCreatedDate();
	}

}

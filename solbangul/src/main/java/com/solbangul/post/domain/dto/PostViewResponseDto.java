package com.solbangul.post.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostViewResponseDto {
	private Long id;

	private String title;

	private Boolean publicYn;

	private Boolean annonyYn;

	private String content;

	private String writer;

	@Enumerated(EnumType.STRING)
	private Category category;

	private Boolean readYn;

	
	private Integer viewCount;

	private LocalDateTime modifiedDate;

	public PostViewResponseDto(Post p) {
		this.title = p.getTitle();
		this.publicYn = p.getPublicYn();
		this.annonyYn = p.getAnnonyYn();
		this.content = p.getContent();
		this.writer = p.getWriter();
		this.category = p.getCategory();
		this.readYn = p.getReadYn();
		this.viewCount = p.getViewCount();
		this.modifiedDate = p.getModifiedDate();
	}

}

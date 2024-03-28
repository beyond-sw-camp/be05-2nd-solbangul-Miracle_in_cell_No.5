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
public class PostFindByRoomListResponseDto {
	private Long id;
	private String title;
	private Category category;
	private String writer;
	private Boolean annonyYn;
	private LocalDateTime createDate;

	public PostFindByRoomListResponseDto(Post p) {
		this.id = p.getId();
		this.title = p.getTitle();
		this.category = p.getCategory();
		this.writer = p.getWriter();
		this.annonyYn = p.getAnnonyYn();
		this.createDate = p.getCreatedDate();
	}

}

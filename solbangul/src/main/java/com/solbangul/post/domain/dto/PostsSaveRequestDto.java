package com.solbangul.post.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;
import com.solbangul.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {
	private Long id;

	private Room room;

	private String title;

	private Boolean publicYn;

	private Boolean annonyYn;

	private String content;

	private String writer;

	private Boolean deleteYn;

	@Enumerated(EnumType.STRING)
	private Category category;

	private Boolean readYn;

	@Builder
	public PostsSaveRequestDto(Room room, String title, Boolean publicYn, Boolean annonyYn, String content,
		String writer, Boolean deleteYn, Category category, Boolean readYn) {
		this.room = room;
		this.title = title;
		this.publicYn = publicYn;
		this.annonyYn = annonyYn;
		this.content = content;
		this.writer = writer;
		this.deleteYn = deleteYn;
		this.category = category;
		this.readYn = readYn;
	}

	@Builder
	public PostsSaveRequestDto(Boolean deleteYn, Boolean readYn) {
		this.deleteYn = deleteYn;
		this.readYn = readYn;
	}
	// @Builder
	// public PostsSaveRequestDto(Room room, String title, Boolean publicYn, Boolean annonyYn, String content, String writer, Category category) {
	// 	this.room = room;
	// 	this.title = title;
	// 	this.publicYn = publicYn;
	// 	this.annonyYn = annonyYn;
	// 	this.content = content;
	// 	this.writer = writer;
	// 	this.deleteYn = false;
	// 	this.category = category;
	// 	this.readYn = false;
	// }

	public Post toEntity() {
		return Post.builder()
			.room(room)
			.title(title)
			.publicYn(publicYn)
			.annonyYn(annonyYn)
			.content(content)
			.writer(writer)
			.deleteYn(deleteYn)
			.category(category)
			.readYn(readYn)
			.build();
	}
}

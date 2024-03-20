package com.solbangul.post.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;
import com.solbangul.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRoomResponse {

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
	public PostRoomResponse(Long id, Room room, String title, Boolean publicYn, Boolean annonyYn, String content,
		String writer, Boolean deleteYn, Category category,
		Boolean readYn) {
		this.id = id;
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

	public PostRoomResponse(Post post) {
		this.id = post.getId();
		this.room = post.getRoom();
		this.title = post.getTitle();
		this.publicYn = post.getPublicYn();
		this.annonyYn = post.getAnnonyYn();
		this.content = post.getContent();
		this.writer = post.getWriter();
		this.deleteYn = post.getDeleteYn();
		this.category = post.getCategory();
		this.readYn = post.getReadYn();
	}

	public void addRoom(Room room) {
		this.room = room;
	}
}

package com.solbangul.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.solbangul.BaseTimeEntity;
import com.solbangul.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id") // foreign key 설정
	private Room room;

	private String title;

	private Boolean publicYn;

	private Boolean annonyYn;

	@Column(name = "post_content")
	private String content;

	private String writer;

	@Enumerated(EnumType.STRING)
	private Category category;

	private Boolean readYn;

	private Integer viewCount;

	@Builder
	public Post(Room room, String title, Boolean publicYn, Boolean annonyYn, String content,
		String writer, Category category, Boolean readYn, Integer viewCount) {
		this.room = room;
		this.title = title;
		this.publicYn = publicYn;
		this.annonyYn = annonyYn;
		this.content = content;
		this.writer = writer;
		this.category = category;
		this.readYn = readYn;
		this.viewCount = viewCount;
	}

	public void update(String title, Boolean publicYn, Boolean annonyYn, String content, Category category) {
		this.title = title;
		this.publicYn = publicYn;
		this.annonyYn = annonyYn;
		this.content = content;
		this.category = category;
	}
}
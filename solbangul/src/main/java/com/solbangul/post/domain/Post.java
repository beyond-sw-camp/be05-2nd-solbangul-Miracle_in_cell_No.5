package com.solbangul.post.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private Boolean publicYn;

	@Column(nullable = false)
	private Boolean annonyYn;

	@Column(name = "post_content", nullable = false)
	private String content;

	private LocalDateTime deleteTime;

	@Column(nullable = false)
	private String writer;

	@Column(nullable = false)
	private Boolean deleteYn;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private Boolean readYn;

	@Builder
	public Post(Room room, String title, Boolean publicYn, Boolean annonyYn, String content, LocalDateTime deleteTime,
		String writer, Boolean deleteYn, String category, Boolean readYn) {
		this.room = room;
		this.title = title;
		this.publicYn = publicYn;
		this.annonyYn = annonyYn;
		this.content = content;
		this.deleteTime = deleteTime;
		this.writer = writer;
		this.deleteYn = deleteYn;
		this.category = category;
		this.readYn = readYn;
	}
}
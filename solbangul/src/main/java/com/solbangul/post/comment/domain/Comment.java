package com.solbangul.post.comment.domain;

import java.time.LocalDateTime;

import com.solbangul.post.domain.Post;
import com.solbangul.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(nullable = false)
	private String writer;

	@Column(name = "comment_content", nullable = false)
	private String content;

	private LocalDateTime writeTime;

	private LocalDateTime updateTime;

	private LocalDateTime deleteTime;

	@Column(nullable = false)
	private Boolean deleteYn;

	@Builder
	public Comment(User user, Post post, String writer, String content, LocalDateTime writeTime,
		LocalDateTime updateTime,
		LocalDateTime deleteTime, Boolean deleteYn) {
		this.user = user;
		this.post = post;
		this.writer = writer;
		this.content = content;
		this.writeTime = writeTime;
		this.updateTime = updateTime;
		this.deleteTime = deleteTime;
		this.deleteYn = deleteYn;
	}
}

package com.solbangul.post.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.solbangul.BaseTimeEntity;
import com.solbangul.post.domain.Post;
import com.solbangul.user.domain.User;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

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

	@Builder
	public Comment(User user, Post post, String writer, String content) {
		this.user = user;
		this.post = post;
		this.writer = writer;
		this.content = content;
	}
}

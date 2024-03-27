package com.solbangul.post.comment.domain.dto;

import com.solbangul.post.comment.domain.Comment;
import com.solbangul.post.domain.Post;
import com.solbangul.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Builder
public class CommentSaveDto {
	private Long id;
	private Long userId;
	private Long postId;
	private String writer;
	private String content;

	public CommentSaveDto(Long id, Long userId, Long postId, String writer, String content) {
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.writer = writer;
		this.content = content;
	}

	public Comment toEntity(User user, Post post) {
		return Comment.builder()
			.user(user)
			.post(post)
			.writer(writer)
			.content(content)
			.build();
	}
}

package com.solbangul.post.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.comment.domain.Comment;
import com.solbangul.post.comment.domain.dto.CommentSaveDto;
import com.solbangul.post.comment.repository.CommentRepository;
import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.room.repository.RoomRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public Long save(CommentSaveDto commentSaveDto) {
		Post receiverPost = postRepository.findById(commentSaveDto.getPostId()).orElseThrow();

		User senderUser = userRepository.findById(commentSaveDto.getUserId()).orElseThrow();

		Comment comment = commentSaveDto.toEntity(senderUser, receiverPost);

		Comment savedComment = commentRepository.save(comment);

		return savedComment.getId();
	}
}

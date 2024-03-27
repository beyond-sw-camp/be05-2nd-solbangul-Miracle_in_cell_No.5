package com.solbangul.post.comment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.solbangul.post.comment.domain.dto.CommentSaveDto;
import com.solbangul.post.comment.repository.CommentRepository;
import com.solbangul.post.comment.service.CommentService;
import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentService commentService;
	private final UserService userService;
	private final CommentRepository commentRepository;

	@PostMapping("/room/{roomId}/post/{postId}/comment")
	public String addComment(@PathVariable("roomId") Long roomId,
		@PathVariable("postId") Long postId,
		@ModelAttribute CommentSaveDto commentSaveDto,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		Long userId = customUserDetails.getAuthenticatedUser().getId();
		User authenticatedUser = userService.findOne(userId);

		commentSaveDto.setUserId(userId);
		commentSaveDto.setPostId(postId);
		commentSaveDto.setWriter(authenticatedUser.getNickname());

		log.info(commentSaveDto.toString());
		String content = commentSaveDto.getContent();
		commentSaveDto.setContent(content);

		Long commentId = commentService.save(commentSaveDto);

		return "redirect:/room/" + roomId + "/post/" + postId + "/view";
	}
}

package com.solbangul.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.post.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final PostRepository postRepository;
	private final PostService postService;

	@GetMapping
	public String viewAllPosts(Model model) {
		List<Post> posts = postRepository.findAll();
		model.addAttribute("posts", posts);

		return "admin";
	}

	@PostMapping("/delete")
	public String deleteByAdmin(@RequestParam("postId") Long postId) {
		postService.delete(postId);
		return "redirect:/admin";
	}

}


package com.solbangul.post.controller;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solbangul.post.domain.dto.PostsSaveRequestDto;
import com.solbangul.post.service.PostService;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/room/post")
@Slf4j
public class PostController {

	@Resource(name = "post")
	private PostService postService;

	@Resource(name = "room")
	private RoomService roomService;

	@GetMapping("/{id}/save")
	public String getSave(@PathVariable Long id, Model model) {
		model.addAttribute("room_id", id);
		model.addAttribute("posts", new PostsSaveRequestDto());
		return "save_post";
	}

	@PostMapping("/{id}/save")
	public String postsSave(@PathVariable("id") Long id, PostsSaveRequestDto requestDto) {
		// 방에 들어와서 글을 쓴다. 지금 들어와 있는 room_id = id
		// id를 갖는 room 찾아서 Post에 room 객체를 넣고, not null 조건 채워준다..
		// 즉, 지금 들어와 있는 room을  post에 객체로 넣어주는 것,,! (일대다 관계에서 조인 방법)
		RoomResponseDto roomResponseDto = roomService.findById(id);
		// User user = userRepository.findByRoom(roomResponseDto.toEntity());
		requestDto.setRoom(roomResponseDto.toEntity());
		requestDto.setWriter(roomResponseDto.getUser().getNickname());
		requestDto.setDeleteYn(false);
		requestDto.setReadYn(false);
		// 게시물 저장
		Long post_id = postService.save(requestDto);
		System.out.println("debug >>>> postsSave() post_id : " + post_id);
		return "redirect:/room/view/" + id;
	}

}

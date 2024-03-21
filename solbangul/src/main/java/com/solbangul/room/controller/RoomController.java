package com.solbangul.room.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solbangul.post.domain.Post;
import com.solbangul.post.domain.dto.PostRoomResponse;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {

	@Resource(name = "room")
	private RoomService roomService;

	@Autowired
	private PostRepository postRepository;

	// 해당 룸이 내 룸인가 다른 사람 룸인가
	@GetMapping("/view/{id}")
	public String viewRoom(@PathVariable Long id, Model model) {
		RoomResponseDto response = roomService.findById(id);
		//response의 userid와 로그인 된 유저의 아이디 비교 (서비스에서 하장)
		model.addAttribute("roomInfo", response);
		return "view";
	}

	// 해당 룸에 존재하는 글들
	@GetMapping("/view/{id}/view_posts")
	public String viewPosts(@PathVariable Long id, Model model) {
		//post에 대한 findByRoom 이런 식이나 findAll 해서 다 가져 오거나 해야 할 듯,,,
		System.out.println("viewPosts () room id : " + id);
		// List<RoomPostResponseDto> list = postRepository.findByRoom(new RoomPostRequestDto(id)) ;
		// 모든 post 불러오고... (findAll) 각 post의 room_id가 지금 들어와 있는 room_id와 같은지 보기
		// -> 보고 싶은건 지금 들어와 있는 방의 글들 !!!
		List<Post> list = postRepository.findAll();
		List<PostRoomResponse> postList = new ArrayList<>();
		for (Post response : list) {
			if (response.getRoom().getId() == id) {
				postList.add(new PostRoomResponse(response));
				System.out.println("debug >>> post : " + response);
			}
		}
		model.addAttribute("postList", postList);
		return "view_posts";
	}
}

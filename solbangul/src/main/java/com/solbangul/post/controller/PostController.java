package com.solbangul.post.controller;

import jakarta.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.solbangul.post.domain.dto.PostEditRequestDto;
import com.solbangul.post.domain.dto.PostViewResponseDto;
import com.solbangul.post.domain.dto.PostsSaveRequestDto;
import com.solbangul.post.service.PostService;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.service.RoomService;
import com.solbangul.user.domain.dto.AuthenticatedUserDto;
import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/room/{room_id}/post")
@Slf4j
public class PostController {

	@Resource(name = "post")
	private PostService postService;

	@Resource(name = "room")
	private RoomService roomService;

	@GetMapping("/save")
	public String getSave(@PathVariable(name = "room_id") Long id, Model model) {
		model.addAttribute("room_id", id);
		model.addAttribute("posts", new PostsSaveRequestDto());
		return "save_post";
	}

	@PostMapping("/save")
	public String postsSave(@PathVariable(name = "room_id") Long id, PostsSaveRequestDto requestDto,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 방에 들어와서 글을 쓴다. 지금 들어와 있는 room_id = id
		// id를 갖는 room 찾아서 Post에 room 객체를 넣고, not null 조건 채워준다..
		// 즉, 지금 들어와 있는 room을  post에 객체로 넣어주는 것,,! (일대다 관계에서 조인 방법)
		RoomResponseDto roomResponseDto = roomService.findById(id);
		AuthenticatedUserDto authenticatedUserDto = customUserDetails.getAuthenticatedUser();
		requestDto.setRoom(roomResponseDto.toEntity());
		requestDto.setWriter(authenticatedUserDto.getNickname());
		requestDto.setDeleteYn(false);
		requestDto.setReadYn(false);
		// 게시물 저장
		Long post_id = postService.save(requestDto);
		System.out.println("debug >>>> postsSave() post_id : " + post_id);
		return "redirect:/room/" + id + "/view_room";
	}

	@GetMapping("/{post_id}/view")
	public String postsView(@PathVariable(name = "room_id") Long room_id, @PathVariable(name = "post_id") Long id,
		Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 여기서 id는 post_id
		PostViewResponseDto response = postService.findById(id);
		AuthenticatedUserDto authenticatedUserDto = customUserDetails.getAuthenticatedUser();
		model.addAttribute("room_id", room_id);
		model.addAttribute("post_id", id);
		model.addAttribute("postInfo", response); // writer
		model.addAttribute("userInfo", authenticatedUserDto); // nickname

		return "view_post";
	}

	@GetMapping("/{post_id}/edit")
	public String updateForm(@PathVariable(name = "room_id") Long room_id, @PathVariable(name = "post_id") Long id,
		Model model) {
		// 여기서 id는 post_id
		PostEditRequestDto dto = postService.editFindById(id);
		model.addAttribute("room_id", room_id);
		model.addAttribute("post_id", id);
		model.addAttribute("postInfo", dto);
		return "edit_post";
	}

	@PostMapping("/{post_id}/edit") //submit 눌렀을 때 ,,
	public String update(@PathVariable(name = "room_id") Long room_id, @PathVariable(name = "post_id") Long id,
		PostEditRequestDto requestDto) {
		postService.update(id, requestDto);
		return "redirect:/room/" + room_id + "/post/" + id + "/view";
	}

	@RequestMapping(value = "/{post_id}/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@PathVariable(name = "room_id") Long room_id, @PathVariable(name = "post_id") Long id) {
		// 여기서 id는 post_id
		postService.delete(id);
		return "redirect:/room/" + room_id + "/view/view_posts";
	}

}

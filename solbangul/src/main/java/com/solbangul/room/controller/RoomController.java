package com.solbangul.room.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.solbangul.post.domain.dto.PostFindByRoomListResponseDto;
import com.solbangul.post.domain.dto.PostSearchListResponseDto;
import com.solbangul.post.service.PostService;
import com.solbangul.room.domain.dto.RoomEditResponseDto;
import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.service.RoomService;
import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/room")
@Controller
public class RoomController {

	private final RoomService roomService;
	private final PostService postService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model) {
		List<RoomListResponseDto> list = roomService.findAll();
		model.addAttribute("roomList", list);
		for (RoomListResponseDto response : list) {
			log.info("debug >>> room={}", response);
		}
		return "main";
	}

	@GetMapping("/{room_id}/view")
	public String viewRoom(@PathVariable(name = "room_id") Long id, Model model,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		RoomResponseDto response = roomService.findById(id);

		Long userId = customUserDetails.getAuthenticatedUser().getId();
		User authenticatedUser = userService.findOne(userId);
		model.addAttribute("roomInfo", response);
		model.addAttribute("userInfo", authenticatedUser);

		//response의 LoginId와 로그인 된 유저의 로그인 아이디 비교 or html 파일에서 if문,,
		log.info("debug >>> 현재 방 주인 id={}", response.getLoginId());
		log.info("debug >>> 로그인 한 사람 id={}", authenticatedUser.getLoginId());
		return "view_room";
	}

	@GetMapping("/{room_id}/view_posts")
	public String viewPosts(@PathVariable(name = "room_id") Long id, Model model,
		@RequestParam(value = "keyword", defaultValue = "") String keyword,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		log.info("viewPosts roomId={}", id);

		Page<PostFindByRoomListResponseDto> postDtos = postService.findPostsByRoomId(id, pageable);
		List<PostFindByRoomListResponseDto> postList = postDtos.getContent();
		int totalPages = postDtos.getTotalPages();  // 총 페이지 수 얻기

		model.addAttribute("room_id", id);
		model.addAttribute("postList", postList);
		model.addAttribute("totalPages", totalPages - 1);
		model.addAttribute("pageable", pageable);
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		model.addAttribute("keyword", keyword);

		return "view_postList";
	}

	@GetMapping("/{room_id}/search")
	public String search(@PathVariable(name = "room_id") Long id,
		@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<PostSearchListResponseDto> postDtos = postService.search(keyword, pageable);
		List<PostSearchListResponseDto> postList = postDtos.getContent();
		int totalPages = postDtos.getTotalPages();  // 총 페이지 수 얻기

		model.addAttribute("room_id", id);
		model.addAttribute("postList", postList);
		model.addAttribute("pageable", pageable);
		model.addAttribute("totalPages", totalPages - 1);
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		model.addAttribute("keyword", keyword);

		return "view_search_postList";
	}

	@GetMapping("/{room_id}/edit")
	public String updateForm(@PathVariable(name = "room_id") Long id, Model model) {
		RoomEditResponseDto dto = roomService.editFindById(id);
		log.info("updateForm >>>>> {}", dto.getRoomName());

		model.addAttribute("roomInfo", dto);
		model.addAttribute("room_id", id);
		return "edit_room";
	}

	@PostMapping("/{room_id}/edit") //submit 눌렀을 때 ,,
	public String update(@PathVariable(name = "room_id") Long id, @RequestParam String roomName,
		@RequestParam String introduction) {
		RoomEditResponseDto requestDto = new RoomEditResponseDto(introduction, roomName);
		roomService.update(id, requestDto);

		return "redirect:/room/" + id + "/view";
	}
}

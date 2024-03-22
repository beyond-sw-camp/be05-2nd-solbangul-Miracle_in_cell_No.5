package com.solbangul.room.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.room.domain.dto.RoomEditResponseDto;
import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.service.RoomService;
import com.solbangul.user.domain.dto.AuthenticatedUserDto;
import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/room")
@Controller
public class RoomController { // TODO: log 사용하기, 변수명 좀 더 가독성 좋게 바꾸기, 검증 로직 추가하기

	private final RoomService roomService;
	private final PostRepository postRepository;

	@GetMapping("/list")
	public String list(Model model) {
		List<RoomListResponseDto> list = roomService.findAll();
		model.addAttribute("roomList", list);
		for (RoomListResponseDto response : list) {
			System.out.println("debug >>> room : " + response);
		}
		return "main";
	}

	// 해당 룸이 내 룸인가 다른 사람 룸인가
	@GetMapping("/{room_id}/view")
	public String viewRoom(@PathVariable(name = "room_id") Long id, Model model,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		RoomResponseDto response = roomService.findById(id);

		AuthenticatedUserDto authenticatedUser = customUserDetails.getAuthenticatedUser();
		model.addAttribute("roomInfo", response);
		model.addAttribute("userInfo", authenticatedUser);

		//response의 LoginId와 로그인 된 유저의 로그인 아이디 비교 or html 파일에서 if문,,
		System.out.println("debug >>> 현재 방 주인 id:" + response.getUser().getLoginId());
		System.out.println("debug >>> 로그인 한 사람 id:" + authenticatedUser.getLoginId());
		return "view_room";
	}

	// 해당 룸에 존재하는 글들
	@GetMapping("/{room_id}/view/view_posts")
	public String viewPosts(@PathVariable(name = "room_id") Long id, Model model) {
		log.info("viewPosts roomId={}", id);
		// TODO postService 생성
		List<Post> postList = postRepository.findPostsByRoomId(id);

		model.addAttribute("room_id", id);
		model.addAttribute("postList", postList);

		return "view_postList";
	}

	@GetMapping("/{room_id}/edit")
	public String updateForm(@PathVariable(name = "room_id") Long id, Model model) {
		RoomEditResponseDto dto = roomService.editFindById(id);
		System.out.println("updateForm >>>>> " + dto.getRoomName());

		model.addAttribute("room_id", id);
		model.addAttribute("roomInfo", dto);
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

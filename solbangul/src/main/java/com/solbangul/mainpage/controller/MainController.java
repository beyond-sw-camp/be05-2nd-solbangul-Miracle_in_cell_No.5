package com.solbangul.mainpage.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.service.RoomService;
import com.solbangul.speaker.service.SpeakerService;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

	private final RoomService roomService;
	private final UserService userService;
	private final SpeakerService speakerService;

	@GetMapping("/")
	public String mainRoomList(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		model.addAttribute("user", userService.findOne(customUserDetails.getId()));
		List<RoomListResponseDto> list = roomService.findAll();

		model.addAttribute("roomList", list);
		String speakerContent = speakerService.getSpeakerContent();
		model.addAttribute("speakerContent", speakerContent);
		for (RoomListResponseDto response : list) {
			log.info("debug >>> room : {}", response);
		}
		return "main";
	}
}

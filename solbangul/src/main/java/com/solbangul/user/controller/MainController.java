package com.solbangul.user.controller;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.service.RoomService;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.domain.dto.ResponseLoginUserDto;

@Controller
public class MainController {

	@Resource(name = "room")
	private RoomService roomService;

	@GetMapping("/")
	public String mainRoomList(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		ResponseLoginUserDto responseLoginUserDto = customUserDetails.getResponseLoginUserDto();
		model.addAttribute("user", responseLoginUserDto);

		// 모든 room 출력
		List<RoomListResponseDto> list = roomService.findAll();
		model.addAttribute("roomList", list);
		for (RoomListResponseDto response : list) {
			System.out.println("debug >>> room : " + response);
		}
		return "main";
	}

	@GetMapping("/admin")
	public String test() {
		return "test";
	}

}

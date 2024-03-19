package com.solbangul.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.domain.dto.ResponseLoginUserDto;

@Controller
public class MainController {

	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		ResponseLoginUserDto responseLoginUserDto = customUserDetails.getResponseLoginUserDto();
		model.addAttribute("user", responseLoginUserDto);
		return "main";
	}

	@GetMapping("/admin")
	public String test() {
		return "test";
	}
}

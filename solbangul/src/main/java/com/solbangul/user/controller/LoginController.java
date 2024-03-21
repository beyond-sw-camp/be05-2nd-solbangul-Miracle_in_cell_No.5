package com.solbangul.user.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.dto.LoginUserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginForm(HttpServletRequest request, @RequestParam(required = false) String error,
		@RequestParam(required = false) String errorMessage, @ModelAttribute("user") LoginUserDto loginUserDto,
		BindingResult bindingResult) {

		if (request.isUserInRole(Role.ROLE_USER.toString()) || request.isUserInRole(Role.ROLE_ADMIN.toString())) {
			return "redirect:/";
		}

		if ("object".equals(error)) {
			bindingResult.reject("objectError", errorMessage);
		}
		return "login";
	}
}

package com.solbangul.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class JoinController {

	private final UserServiceImpl joinService;

	@GetMapping("/join")
	public String joinForm(Model model, HttpServletRequest request) {
		if (request.isUserInRole(Role.ROLE_USER.toString()) || request.isUserInRole(Role.ROLE_ADMIN.toString())) {
			return "redirect:/";
		}

		model.addAttribute("user", new JoinRequestUserDto());
		return "join";
	}

	@PostMapping("/join")
	public String join(@Valid @ModelAttribute("user") JoinRequestUserDto joinRequestUserDto,
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes) {

		if (joinService.isExistsByLoginId(joinRequestUserDto)) {
			bindingResult.rejectValue("loginId", "unique", "중복되는 아이디 입니다.");
		}
		if (joinService.isExistsByNickname(joinRequestUserDto)) {
			bindingResult.rejectValue("nickname", "unique", "중복되는 닉네임 입니다.");
		}
		if (!joinRequestUserDto.getPassword().equals(joinRequestUserDto.getPasswordConfirm())) {
			bindingResult.rejectValue("passwordConfirm", "confirm", "비밀번호가 일치하지 않습니다.");
		}

		if (bindingResult.hasErrors()) {
			return "join";
		}

		joinService.join(joinRequestUserDto);

		redirectAttributes.addAttribute("status", true);
		return "redirect:/login";
	}
}

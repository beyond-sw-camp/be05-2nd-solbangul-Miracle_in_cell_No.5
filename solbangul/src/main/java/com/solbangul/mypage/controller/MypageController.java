package com.solbangul.mypage.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solbangul.mypage.domain.UpdateUserDto;
import com.solbangul.mypage.service.MypageService;
import com.solbangul.post.domain.Post;
import com.solbangul.post.service.PostService;
import com.solbangul.user.domain.dto.CustomUserDetails;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

	private final MypageService mypageService;
	private final UserService userService;

	@GetMapping
	public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
		return "mypage";
	}

	@GetMapping("/edit")
	public String editUserInfo(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
		return "edit_user_info";
	}

	@PostMapping("/update")
	public String updateUserInfo(@Valid @ModelAttribute("user") UpdateUserDto updateUserDto,
		BindingResult bindingResult,
		@AuthenticationPrincipal CustomUserDetails currentUser) {
		String loginId = currentUser.getUsername();
		if (userService.isExistsByNickname(updateUserDto.getNickname())) {
			bindingResult.rejectValue("nickname", "unique", "중복되는 닉네임 입니다.");
		}
		if (bindingResult.hasErrors()) {
			return "edit_user_info";
		}

		mypageService.updateUserInfo(updateUserDto, loginId);
		return "redirect:/mypage";
	}

	@GetMapping("/myposts")
	public String getMyPosts(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Post> myPosts = mypageService.getMyPosts(currentUser.getAuthenticatedUser().getNickname());
		model.addAttribute("myPosts", myPosts);
		return "myposts";
	}

}
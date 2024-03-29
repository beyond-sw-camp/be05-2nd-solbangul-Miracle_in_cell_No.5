package com.solbangul.mypage.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.solbangul.mypage.domain.UpdateUserPasswordDto;
import com.solbangul.mypage.service.MypageService;
import com.solbangul.post.domain.Post;
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

	@GetMapping("/profileImgEdit")
	public String showProfileImgEditForm(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", mypageService.getMypageProfileInfo(currentUser.getUsername()));
		return "edit_profile";
	}

	@GetMapping("/nicknameEdit")
	public String showNicknameEditForm(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
		return "edit_nickname";
	}

	@GetMapping("/pwdEdit")
	public String showPasswordEditForm(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
		return "edit_password";
	}

	@PostMapping("/updateProfilePicture")
	public String updateProfilePicture(@RequestParam("multipartFile") MultipartFile multipartFile,
		@AuthenticationPrincipal CustomUserDetails currentUser) {
		mypageService.updateProfilePicture(multipartFile, currentUser.getUsername());
		return "redirect:/mypage";
	}

	@PostMapping("/updateNickname")
	public String updateNickname(@RequestParam("nickname") String nickname,
		@AuthenticationPrincipal CustomUserDetails currentUser) {
		mypageService.updateNickname(nickname, currentUser.getUsername());
		return "redirect:/mypage";
	}

	@PostMapping("/updatePassword")
	public String updatePassword(@Valid @ModelAttribute("user") UpdateUserPasswordDto updateUserPasswordDto,
		BindingResult bindingResult,
		@AuthenticationPrincipal CustomUserDetails currentUser) {
		if (bindingResult.hasErrors()) {
			return "edit_password";
		}
		mypageService.updatePassword(updateUserPasswordDto.getPassword(), currentUser.getUsername());
		return "redirect:/mypage";
	}

	@GetMapping("/myposts")
	public String getMyPosts(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Post> myPosts = mypageService.getMyPosts(currentUser.getAuthenticatedUser().getRoom().getId());
		model.addAttribute("myPosts", myPosts);
		return "myposts";
	}

}
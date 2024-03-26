package com.solbangul.speaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.solbangul.speaker.domain.dto.SpeakerDto;
import com.solbangul.speaker.service.SpeakerService;
import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SpeakerController {
	private final SpeakerService speakerService;

	@Autowired
	public SpeakerController(SpeakerService speakerService) {
		this.speakerService = speakerService;
	}

	@GetMapping("/speaker")
	public String speakerMain(Model model) { // speaker.html로 이동하기
		model.addAttribute("speakerDto", new SpeakerDto());
		return "speaker";
	}

	@PostMapping("/speaker")
	public String saveSpeaker(@ModelAttribute("speakerDto") SpeakerDto speakerDto,
		Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		String loginId = customUserDetails.getAuthenticatedUser().getLoginId();// 로그인한 유저 정보
		speakerDto.setLoginId(loginId);

		boolean saved = speakerService.saveAnnouncement(speakerDto);
		log.info(saved ? "저장 성공" : "저장 실패");

		model.addAttribute("announcementMessage", speakerDto.getContent());
		model.addAttribute("startTime", speakerDto.getStartTime());
		model.addAttribute("endTime", speakerDto.getEndTime());
		SpeakerDto announcements = speakerService.getAnnouncement();
		model.addAttribute("announcements", announcements);

		return "redirect:/";
	}
}
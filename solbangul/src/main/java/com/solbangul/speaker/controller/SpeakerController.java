package com.solbangul.speaker.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.solbangul.speaker.domain.dto.SpeakerDto;
import com.solbangul.speaker.service.SpeakerService;
import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SpeakerController {

	private final SpeakerService speakerService;

	@GetMapping("/speaker")
	public String speakerMain(Model model) {
		model.addAttribute("speakerDto", new SpeakerDto());
		List<LocalDateTime> reservedSpeakersTime = speakerService.findReservedSpeakers();
		model.addAttribute("reservedSpeakersTime", reservedSpeakersTime);
		return "speaker";
	}

	@PostMapping("/speaker")
	public String saveSpeaker(@Valid @ModelAttribute SpeakerDto speakerDto, BindingResult bindingResult, Model model,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		speakerDto.setLoginId(customUserDetails.getUsername());

		boolean isSuccessSave = speakerService.saveSpeaker(speakerDto);
		if (!isSuccessSave) {
			bindingResult.reject("notEnoughSolbangul", "솔방울이 부족합니다.");
		}
		if (bindingResult.hasErrors()) {
			return "speaker";
		}

		model.addAttribute("speakerDto", speakerDto);
		return "redirect:/";
	}

	private static LocalTime getLocalTime(String reservationTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return LocalTime.parse(reservationTime, formatter);
	}
}
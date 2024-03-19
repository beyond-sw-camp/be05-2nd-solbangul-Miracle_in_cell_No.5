package com.solbangul.user.mail.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.solbangul.user.mail.dto.EmailCheckDto;
import com.solbangul.user.mail.dto.EmailRequestDto;
import com.solbangul.user.mail.service.MailSendService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MailController {

	private final MailSendService mailService;

	@PostMapping("/mailSend")
	public String mailSend(@Valid @RequestBody EmailRequestDto emailDto) {
		System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());
		return mailService.joinEmail(emailDto.getEmail());
	}

	@PostMapping("/mailauthCheck")
	public String AuthCheck(@Valid @RequestBody EmailCheckDto emailCheckDto) {
		Boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
		System.out.println("emailCheckDto = " + emailCheckDto);
		if (Checked) {
			return "ok";
		} else {
			throw new NullPointerException("뭔가 잘못!");
		}
	}
}

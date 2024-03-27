package com.solbangul.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.dto.PasswordResetDto;
import com.solbangul.user.domain.dto.PasswordResetUserDto;
import com.solbangul.user.mail.dto.EmailRequestDto;
import com.solbangul.user.mail.service.MailSendService;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/password")
@RequiredArgsConstructor
@Controller
public class PasswordResetController {

	private final UserService userService;
	private final MailSendService mailService;

	@GetMapping("/step1")
	public String passwordStep1Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		model.addAttribute("mail", new EmailRequestDto());

		return "password/step1";
	}

	@PostMapping("/step1")
	public String mailSend(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto, BindingResult bindingResult,
		HttpSession session) {

		if (!userService.isEmailAlreadyExists(emailRequestDto)) {
			bindingResult.rejectValue("email", "unique", "가입된 이메일이 존재하지 않습니다.");
		}

		if (bindingResult.hasErrors()) {
			return "password/step1";
		}

		mailService.sendEmailForJoin(emailRequestDto.getEmail());
		log.info("이메일 인증 메일 발송, 이메일={}", emailRequestDto.getEmail());

		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/password/step2";
	}

	@GetMapping("/step2")
	public String passwordStep2Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		EmailRequestDto emailRequestDto = getEmailRequestDtoOnSession(request);
		if (emailRequestDto == null) {
			return "redirect:/password/step1";
		}

		model.addAttribute("mail", emailRequestDto);

		return "password/step2";
	}

	@PostMapping("/step2")
	public String authCheck(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto,
		BindingResult bindingResult, HttpSession session) {
		if (!(mailService.CheckAuthNum(emailRequestDto.getEmail(), emailRequestDto.getAuthNum()))) {
			bindingResult.rejectValue("authNum", "authNum", "인증 번호가 다릅니다.");
		}

		if (bindingResult.hasErrors()) {
			return "password/step2";
		}

		log.info("이메일 인증 완료, 이메일={}", emailRequestDto.getEmail());
		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/password/step3";
	}

	@GetMapping("/step3")
	public String passwordStep3Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		EmailRequestDto emailRequestDto = getEmailRequestDtoOnSession(request);
		if (emailRequestDto == null) {
			return "redirect:/password/step1";
		}

		model.addAttribute("user", new PasswordResetDto());

		return "password/step3";
	}

	@PostMapping("/step3")
	public String passwordStep3(@Valid @ModelAttribute("user") PasswordResetDto passwordResetDto,
		BindingResult bindingResult, HttpSession session) {

		EmailRequestDto emailRequestDtoOnSession = getEmailRequestDtoOnSession(session);
		if (emailRequestDtoOnSession == null) {
			return "redirect:/password/step1";
		}
		PasswordResetUserDto userDto = userService.findUserByEmail(emailRequestDtoOnSession.getEmail());
		log.info("userDto={}", userDto.getId());

		if (!passwordResetDto.getResetPassword().equals(passwordResetDto.getConfirmResetPassword())) {
			bindingResult.rejectValue("confirmResetPassword", "confirm",
				"비밀번호가 일치하지 않습니다.");
		}

		if (bindingResult.hasErrors()) {
			return "password/step3";
		}

		userService.updatePassword(userDto.getId(), passwordResetDto.getResetPassword());
		session.removeAttribute("emailRequestDto");

		return "redirect:/login";
	}

	private static EmailRequestDto getEmailRequestDtoOnSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		return getEmailRequestDtoOnSession(session);
	}

	private static EmailRequestDto getEmailRequestDtoOnSession(HttpSession session) {
		return (EmailRequestDto)session.getAttribute("emailRequestDto");
	}

	private boolean isAlreadyLoggedIn(HttpServletRequest request) {
		return request.isUserInRole(Role.ROLE_USER.toString()) || request.isUserInRole(Role.ROLE_ADMIN.toString());
	}
}

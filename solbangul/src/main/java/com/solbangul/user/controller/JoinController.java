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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solbangul.hanwhauser.repository.HanwhaUserRepository;
import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.mail.dto.EmailRequestDto;
import com.solbangul.user.mail.service.MailSendService;
import com.solbangul.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class JoinController {

	private final UserServiceImpl joinService;
	private final MailSendService mailService;
	private final HanwhaUserRepository hanwhaUserRepository;

	@GetMapping("/join-step1")
	public String joinStep1Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		model.addAttribute("mail", new EmailRequestDto());

		return "join-step1";
	}

	@PostMapping("/mailSend")
	public String mailSend(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto, BindingResult bindingResult,
		HttpSession session) {

		if (joinService.isEmailAlreadyExists(emailRequestDto)) {
			bindingResult.rejectValue("email", "unique", "이메일이 이미 존재합니다.");
		}
		if (!isHanwhaUser(emailRequestDto)) {
			bindingResult.rejectValue("email", "hanwha", "한화 SW교육 5기생만 가입 가능합니다.");
		}
		if (bindingResult.hasErrors()) {
			return "join-step1";
		}

		mailService.sendEmailForJoin(emailRequestDto.getEmail());
		log.info("이메일 인증 메일 발송, 이메일={}", emailRequestDto.getEmail());

		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/join-step2";
	}

	@GetMapping("/join-step2")
	public String joinStep2Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/join-step1";
		}
		EmailRequestDto emailRequestDto = (EmailRequestDto)session.getAttribute("emailRequestDto");
		if (emailRequestDto == null) {
			return "redirect:/join-step1";
		}

		model.addAttribute("mail", emailRequestDto);

		return "join-step2";
	}

	@PostMapping("/authCheck")
	public String AuthCheck(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto,
		BindingResult bindingResult, HttpSession session) {
		if (!(mailService.CheckAuthNum(emailRequestDto.getEmail(), emailRequestDto.getAuthNum()))) {
			bindingResult.rejectValue("authNum", "authNum", "인증 번호가 다릅니다.");
		}

		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "join-step2";
		}

		log.info("이메일 인증 완료, 이메일={}", emailRequestDto.getEmail());
		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/join-step3";
	}

	@GetMapping("/join-step3")
	public String joinStep3Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/join-step1";
		}
		EmailRequestDto emailRequestDto = (EmailRequestDto)session.getAttribute("emailRequestDto");
		if (emailRequestDto == null) {
			return "redirect:/join-step1";
		}
		JoinRequestUserDto joinRequestUserDto = new JoinRequestUserDto();
		joinRequestUserDto.setEmail(emailRequestDto.getEmail());
		joinRequestUserDto.setName(hanwhaUserRepository.findHanwhaUserByGitEmail(emailRequestDto.getEmail()).getName());

		model.addAttribute("user", joinRequestUserDto);

		return "join-step3";
	}

	@PostMapping("/join-step3")
	public String joinStep3(@Valid @ModelAttribute("user") JoinRequestUserDto joinRequestUserDto,
		BindingResult bindingResult, HttpSession session,
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
			return "join-step3";
		}

		joinService.join(joinRequestUserDto);
		log.info("{} 회원가입 완료", joinRequestUserDto.getName());
		session.removeAttribute("emailRequestDto");

		redirectAttributes.addAttribute("status", true);
		return "redirect:/login";
	}

	private boolean isHanwhaUser(EmailRequestDto emailRequestDto) {
		return hanwhaUserRepository.existsHanwhaUserByGitEmail(emailRequestDto.getEmail());
	}

	private boolean isAlreadyLoggedIn(HttpServletRequest request) {
		return request.isUserInRole(Role.ROLE_USER.toString()) || request.isUserInRole(Role.ROLE_ADMIN.toString());
	}
}
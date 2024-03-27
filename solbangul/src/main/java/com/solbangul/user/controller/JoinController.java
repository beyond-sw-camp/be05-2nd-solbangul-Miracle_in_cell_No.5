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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solbangul.hanwhauser.repository.HanwhaUserRepository;
import com.solbangul.user.domain.Role;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.mail.dto.EmailRequestDto;
import com.solbangul.user.mail.service.MailSendService;
import com.solbangul.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/join")
@RequiredArgsConstructor
@Controller
public class JoinController {

	private final UserService joinService;
	private final MailSendService mailService;
	private final HanwhaUserRepository hanwhaUserRepository;

	@GetMapping("/step1") // TODO: JoinValidator로 검증 로직 분리
	public String joinStep1Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		model.addAttribute("mail", new EmailRequestDto());

		return "join/step1";
	}

	@PostMapping("/step1")
	public String mailSend(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto, BindingResult bindingResult,
		HttpSession session) {

		// if (joinService.isEmailAlreadyExists(emailRequestDto)) {
		// 	bindingResult.rejectValue("email", "unique", "이메일이 이미 존재합니다.");
		// }
		// if (!isHanwhaUser(emailRequestDto)) {
		// 	bindingResult.rejectValue("email", "hanwha", "한화 SW교육 5기생만 가입 가능합니다.");
		// }

		if (bindingResult.hasErrors()) {
			return "join/step1";
		}

		mailService.sendEmailForJoin(emailRequestDto.getEmail());
		log.info("이메일 인증 메일 발송, 이메일={}", emailRequestDto.getEmail());

		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/join/step2";
	}

	@GetMapping("/step2")
	public String joinStep2Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		EmailRequestDto emailRequestDto = getEmailRequestDtoOnSession(request);
		if (emailRequestDto == null) {
			return "redirect:/join/step1";
		}

		model.addAttribute("mail", emailRequestDto);

		return "join/step2";
	}

	@PostMapping("/step2")
	public String AuthCheck(@Valid @ModelAttribute("mail") EmailRequestDto emailRequestDto,
		BindingResult bindingResult, HttpSession session) {
		if (!(mailService.CheckAuthNum(emailRequestDto.getEmail(), emailRequestDto.getAuthNum()))) {
			bindingResult.rejectValue("authNum", "authNum", "인증 번호가 다릅니다.");
		}

		if (bindingResult.hasErrors()) {
			return "join/step2";
		}

		log.info("이메일 인증 완료, 이메일={}", emailRequestDto.getEmail());
		session.setAttribute("emailRequestDto", emailRequestDto);
		return "redirect:/join/step3";
	}

	@GetMapping("/step3")
	public String joinStep3Form(Model model, HttpServletRequest request) {
		if (isAlreadyLoggedIn(request)) {
			return "redirect:/";
		}

		EmailRequestDto emailRequestDto = getEmailRequestDtoOnSession(request);
		if (emailRequestDto == null) {
			return "redirect:/join/step1";
		}

		JoinRequestUserDto joinRequestUserDto = new JoinRequestUserDto();
		joinRequestUserDto.setEmail(emailRequestDto.getEmail());
		joinRequestUserDto.setProfileImage("faee2f50-d7cf-42d5-9a65-c6269d0ec26b.png");
		// joinRequestUserDto.setName(hanwhaUserRepository.findHanwhaUserByGitEmail(emailRequestDto.getEmail()).getName());
		joinRequestUserDto.setName("테스트 유저");

		model.addAttribute("user", joinRequestUserDto);

		return "join/step3";
	}

	@PostMapping("/step3")
	public String joinStep3(@Valid @ModelAttribute("user") JoinRequestUserDto joinRequestUserDto,
		BindingResult bindingResult, HttpSession session,
		RedirectAttributes redirectAttributes) {

		if (getEmailRequestDtoOnSession(session) == null) {
			log.info("포스트맨 공격");
			return "redirect:/join/step1";
		}

		if (joinService.isExistsByLoginId(joinRequestUserDto)) {
			bindingResult.rejectValue("loginId", "unique", "중복되는 아이디 입니다.");
		}
		if (joinService.isExistsByNickname(joinRequestUserDto.getNickname())) {
			bindingResult.rejectValue("nickname", "unique", "중복되는 닉네임 입니다.");
		}
		if (!joinRequestUserDto.getPassword().equals(joinRequestUserDto.getPasswordConfirm())) {
			bindingResult.rejectValue("passwordConfirm", "confirm", "비밀번호가 일치하지 않습니다.");
		}

		if (bindingResult.hasErrors()) {
			return "join/step3";
		}

		joinService.join(joinRequestUserDto);
		log.info("{} 회원가입 완료", joinRequestUserDto.getName());
		session.removeAttribute("emailRequestDto");

		redirectAttributes.addAttribute("status", true);
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
		EmailRequestDto emailRequestDto = (EmailRequestDto)session.getAttribute("emailRequestDto");
		if (emailRequestDto == null) {
			return null;
		}
		return emailRequestDto;
	}

	private boolean isHanwhaUser(EmailRequestDto emailRequestDto) {
		return hanwhaUserRepository.existsHanwhaUserByGitEmail(emailRequestDto.getEmail());
	}

	private boolean isAlreadyLoggedIn(HttpServletRequest request) {
		return request.isUserInRole(Role.ROLE_USER.toString()) || request.isUserInRole(Role.ROLE_ADMIN.toString());
	}
}

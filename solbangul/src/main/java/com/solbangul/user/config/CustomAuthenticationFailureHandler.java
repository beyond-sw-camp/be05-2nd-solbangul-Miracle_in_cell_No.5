package com.solbangul.user.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String errorName = "object";
		String errorMessage = "아이디 또는 비밀번호를 확인해주세요";

		try {
			throw exception;
		} catch (UsernameNotFoundException e) {
			errorName = "loginId";
			errorMessage = "아이디가 일치하지 않습니다.";
		} catch (BadCredentialsException e) {
			errorName = "object";
			errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
		}

		errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
		setDefaultFailureUrl("/login?error=" + errorName + "&errorMessage=" + errorMessage);
		super.onAuthenticationFailure(request, response, exception);
	}
}

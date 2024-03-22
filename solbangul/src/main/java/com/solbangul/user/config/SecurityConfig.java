package com.solbangul.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/logout", "/join/step1", "/join/step2", "/join/step3", "/mailSend",
					"/authCheck",
					"/error/**", "/css/**", "/img/**", "/js/**").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated()
			);

		http
			.csrf(AbstractHttpConfigurer::disable);

		http
			.formLogin(auth -> auth.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("loginId")
				.failureHandler(new CustomAuthenticationFailureHandler())
				.permitAll());

		http
			.logout(auth -> auth
				.logoutUrl("/logout"));

		http
			.sessionManagement(auth -> auth
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true));

		http
			.sessionManagement(auth -> auth
				.sessionFixation().changeSessionId());

		return http.build();
	}
}

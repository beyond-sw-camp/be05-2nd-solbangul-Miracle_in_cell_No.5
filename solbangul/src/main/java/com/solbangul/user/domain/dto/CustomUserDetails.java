package com.solbangul.user.domain.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.solbangul.user.domain.User;

public class CustomUserDetails implements UserDetails {

	private final User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	// 사용자의 특정 권한에 대해 리턴하는 메서드
	// role 값에 대한 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole().toString();
			}
		});

		return collection;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLoginId();
	}

	// 아래 4가지 메서드
	// 사용자의 id가 만료가 되었는지, 잠겨 있는지, 지금 사용가능한 지에 대해 체크해 주는 메서드
	// 현재는 설정해주지 않았기 때문에 true 반환, DB에 설정해주면 된다.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public AuthenticatedUserDto getAuthenticatedUser() {
		return new AuthenticatedUserDto(user);
	}
}

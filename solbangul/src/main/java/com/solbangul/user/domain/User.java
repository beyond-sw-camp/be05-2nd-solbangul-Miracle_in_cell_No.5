package com.solbangul.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
	@UniqueConstraint(name = "users_login_id_unique", columnNames = {"login_id"})
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String gitEmail;

	private String profilePictureUrl;

	private Integer solbangul;

	protected User() {
	}

	@Builder
	public User(String loginId, String password, String name, String nickname, String gitEmail,
		String profilePictureUrl,
		Integer solbangul) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gitEmail = gitEmail;
		this.profilePictureUrl = profilePictureUrl;
		this.solbangul = solbangul;
	}
}


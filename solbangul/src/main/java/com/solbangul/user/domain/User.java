package com.solbangul.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.solbangul.BaseTimeEntity;
import com.solbangul.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
	@UniqueConstraint(name = "users_login_id_unique", columnNames = {"login_id"})
}) // 유니크 제약조건 설정
@NoArgsConstructor
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String loginId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String gitEmail;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	private String profilePictureUrl;

	private Integer solbangul;

	@Builder
	public User(String loginId, String password, String name, String nickname, String gitEmail, Role role,
		String profilePictureUrl) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gitEmail = gitEmail;
		this.role = role;
		this.profilePictureUrl = profilePictureUrl;
		this.solbangul = 0;
	}

	// 회원가입 시 기본값 설정
	@Builder
	public User(String loginId, String password, Role role) {
		this.loginId = loginId;
		this.password = password;
		this.role = role;
		this.name = "이름";
		this.nickname = "닉네임";
		this.gitEmail = "이메일";
		this.profilePictureUrl = "프로필사진";
		this.solbangul = 0;
	}

	public void addRoom(Room room) {
		this.room = room;
		room.setUser(this);
	}
}
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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
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

	@Setter
	private String profileImage;

	private Integer solbangul;

	@Builder
	public User(String loginId, String password, String name, String nickname, String gitEmail, Role role,
		String profileImage, Integer solbangul) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gitEmail = gitEmail;
		this.role = role;
		this.profileImage = profileImage;
		this.solbangul = solbangul;

	}

	// 연관관계 편의 메서드
	public void addRoom(Room room) {
		this.room = room;
		room.setUser(this);
	}

	public void addSolbangul(int amount) {
		if (amount <= 0) {
			return;
		}
		this.solbangul += amount;
		log.info("{}님 의 솔방울이 {}개 올랐습니다.", loginId, amount);
	}

	public boolean subSolbangul(int amount) {
		if (amount <= 0) {
			return false;
		}
		if ((solbangul - amount) < 0) {
			log.info("{}님 의 솔방울이 부족합니다. 현재 솔방울: {}, 사용할 솔방울: {}", loginId, solbangul, amount);
			return false;
		}
		this.solbangul -= amount;
		log.info("{}님 의 솔방울을 {}개 사용하였습니다.", loginId, amount);
		return true;
	}
}
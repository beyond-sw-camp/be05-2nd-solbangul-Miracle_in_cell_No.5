package com.solbangul.room.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.solbangul.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	@Column(nullable = false)
	private String introduction;

	@OneToOne(mappedBy = "room")
	private User user;

	@Column(nullable = false)
	private String roomName;

	@Builder
	public Room(Long id, User user, String introduction, String roomName) {
		this.id = id;
		this.user = user;
		this.introduction = introduction;
		this.roomName = roomName;
	}

	@Builder
	public Room(User user, String introduction, String roomName) {
		this.user = user;
		this.introduction = introduction;
		this.roomName = roomName;
	}

	// @Builder
	// public Room(Long id, String introduction, String roomName) {
	// 	this.id = id;
	// 	this.introduction = introduction;
	// 	this.roomName = roomName;
	// }
	//
	// @Builder
	// public Room(String introduction, String roomName) {
	// 	this.introduction = introduction;
	// 	this.roomName = roomName;
	// }
}
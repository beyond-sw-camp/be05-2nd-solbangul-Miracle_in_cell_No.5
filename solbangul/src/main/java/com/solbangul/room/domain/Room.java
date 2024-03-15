package com.solbangul.room.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

	@Column(nullable = false)
	private String roomName;

	@Builder
	public Room(String introduction, String roomName) {
		this.introduction = introduction;
		this.roomName = roomName;
	}
}
package com.solbangul.room.domain.dto;

import com.solbangul.room.domain.Room;
import com.solbangul.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

	private Long id;

	private User user;

	private String introduction;

	private String roomName;

	@Builder
	public RoomResponseDto(Long id, User user, String introduction, String roomName) {
		this.id = id;
		this.user = user;
		this.introduction = introduction;
		this.roomName = roomName;
	}

	public RoomResponseDto(Room room) {
		this.id = room.getId();
		this.user = room.getUser();
		this.introduction = room.getIntroduction();
		this.roomName = room.getRoomName();
	}

	public Room toEntity() {
		return Room.builder()
			.id(id)
			.user(user)
			.introduction(introduction)
			.roomName(roomName)
			.build();
	}

	// @Builder
	// public RoomResponseDto(Long id, String introduction, String roomName) {
	// 	this.id = id;
	// 	this.introduction = introduction;
	// 	this.roomName = roomName;
	// }
	//
	// public RoomResponseDto(Room room) {
	// 	this.id = room.getId();
	// 	this.introduction = room.getIntroduction();
	// 	this.roomName = room.getRoomName();
	// }
	//
	// public Room toEntity() {
	// 	return Room.builder()
	// 		.id(id)
	// 		.introduction(introduction)
	// 		.roomName(roomName)
	// 		.build();
	// }
}

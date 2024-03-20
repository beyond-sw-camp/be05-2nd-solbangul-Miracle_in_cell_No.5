package com.solbangul.room.domain.dto;

import com.solbangul.room.domain.Room;
import com.solbangul.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomListResponseDto {
	private Long id;

	private User user;

	private String introduction;

	private String roomName;

	@Builder
	public RoomListResponseDto(Long id, User user, String introduction, String roomName) {
		this.id = id;
		this.user = user;
		this.introduction = introduction;
		this.roomName = roomName;
	}

	public RoomListResponseDto(Room room) {
		this.id = room.getId();
		this.user = room.getUser();
		this.introduction = room.getIntroduction();
		this.roomName = room.getRoomName();
	}
	// @Builder
	// public RoomListResponseDto(Long id, User user, String introduction, String roomName) {
	// 	this.id = id;
	// 	this.introduction = introduction;
	// 	this.roomName = roomName;
	// }
	//
	// public RoomListResponseDto(Room room) {
	// 	this.id = room.getId();
	// 	this.introduction = room.getIntroduction();
	// 	this.roomName = room.getRoomName();
	// }

}

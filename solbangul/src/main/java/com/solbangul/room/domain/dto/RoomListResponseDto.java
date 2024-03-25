package com.solbangul.room.domain.dto;

import com.solbangul.room.domain.Room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomListResponseDto {
	private Long id;

	private String profileImage;

	private String introduction;

	private String roomName;

	public RoomListResponseDto(Room room) {
		this.id = room.getId();
		this.profileImage = room.getUser().getProfileImage();
		this.introduction = room.getIntroduction();
		this.roomName = room.getRoomName();
	}

}

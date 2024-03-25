package com.solbangul.room.domain.dto;

import com.solbangul.room.domain.Room;
import com.solbangul.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

	private Long id;

	private Long userId;

	private String loginId;

	private String profileImage;

	private String introduction;

	private String roomName;

	public RoomResponseDto(Room room) {
		this.id = room.getId();
		this.userId = room.getId();
		this.loginId = room.getUser().getLoginId();
		this.profileImage = room.getUser().getProfileImage();
		this.introduction = room.getIntroduction();
		this.roomName = room.getRoomName();
	}

	public Room toEntity(User user) {
		return Room.builder()
			.id(id)
			.user(user)
			.introduction(introduction)
			.roomName(roomName)
			.build();
	}

}

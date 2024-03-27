package com.solbangul.room.domain.dto;

import com.solbangul.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RoomEditResponseDto {
	private String introduction;
	private String roomName;

	@Builder
	public RoomEditResponseDto(String introduction, String roomName) {
		this.introduction = introduction;
		this.roomName = roomName;
	}

	public RoomEditResponseDto(Room room) {
		this.introduction = room.getIntroduction();
		this.roomName = room.getRoomName();
	}
}

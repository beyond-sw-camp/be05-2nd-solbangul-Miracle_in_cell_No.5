package com.solbangul.room.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.room.domain.Room;
import com.solbangul.room.domain.dto.RoomEditResponseDto;
import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RoomService {
	private final RoomRepository roomRepository;

	public List<RoomListResponseDto> findAll() {
		List<RoomListResponseDto> roomList = new ArrayList<>();
		List<Room> rooms = roomRepository.findAllWithUser();
		for (Room r : rooms) {
			roomList.add(new RoomListResponseDto(r));
		}

		return roomList;
	}

	// 한 회원의 방
	public RoomResponseDto findById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		return new RoomResponseDto(room);
	}

	public RoomEditResponseDto editFindById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		return new RoomEditResponseDto(room);
	}

	@Transactional
	public void update(Long id, RoomEditResponseDto requestDto) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));
		room.update(requestDto.getIntroduction(), requestDto.getRoomName());
	}

	public Room getRoom(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));
		return room;
	}
}

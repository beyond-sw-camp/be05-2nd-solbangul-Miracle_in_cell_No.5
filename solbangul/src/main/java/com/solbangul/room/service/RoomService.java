package com.solbangul.room.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.room.domain.Room;
import com.solbangul.room.domain.dto.RoomListResponseDto;
import com.solbangul.room.domain.dto.RoomResponseDto;
import com.solbangul.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service("room")
public class RoomService {
	private final RoomRepository roomRepository;

	// save 회원 가입과 동시에 ?
	// public void save(){
	//
	// }

	// 전체 방 list (방1, 방2 ...)
	public List<RoomListResponseDto> findAll() {
		List<RoomListResponseDto> roomList = new ArrayList<>();
		List<Room> rooms = roomRepository.findAll();
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
}

package com.solbangul.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solbangul.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

package com.solbangul.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solbangul.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query("select r from Room r join fetch r.user")
	List<Room> findAllWithUser();
}

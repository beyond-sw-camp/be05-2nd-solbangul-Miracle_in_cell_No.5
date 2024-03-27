package com.solbangul.speaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solbangul.speaker.domain.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

	@Query("select s.content from Speaker s where NOW() between s.startTime and s.endTime")
	String findSpeakerForCurrentTime();

	@Query("select s.startTime from Speaker s where NOW() < s.endTime")
	List<LocalDateTime> findReservedSpeakers();
}

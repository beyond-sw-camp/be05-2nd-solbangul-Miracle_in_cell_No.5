package com.solbangul.speaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solbangul.speaker.domain.Speaker;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
	List<Speaker> findAllByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime,
		LocalDateTime endTime);
}

package com.solbangul.speaker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.speaker.domain.Speaker;
import com.solbangul.speaker.domain.dto.SpeakerDto;
import com.solbangul.speaker.repository.SpeakerRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpeakerService {

	private final SpeakerRepository speakerRepository;
	private final UserRepository userRepository;

	public String getSpeakerContent() {
		return speakerRepository.findSpeakerForCurrentTime();
	}

	public List<LocalDateTime> findReservedSpeakers() {
		return speakerRepository.findReservedSpeakers();
	}

	@Transactional
	public boolean saveSpeaker(SpeakerDto speakerDto) {
		User user = userRepository.findByLoginId(speakerDto.getLoginId());
		LocalDateTime startTime = getLocalDateTime(speakerDto);
		Speaker speaker = Speaker.builder()
			.startTime(startTime)
			.endTime(startTime.plusMinutes(30))
			.content(speakerDto.getContent())
			.user(user)
			.build();

		if (user.subSolbangul(10)) {
			speakerRepository.save(speaker);
			return true;
		} else {
			return false;
		}
	}

	private static LocalDateTime getLocalDateTime(SpeakerDto speakerDto) {
		LocalDate reservationDate = speakerDto.getReservationDate();
		LocalTime reservationTime = speakerDto.getReservationTime();
		return LocalDateTime.of(reservationDate, reservationTime);
	}
}

package com.solbangul.speaker.service;

import java.util.List;
import java.util.stream.Collectors;

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

	@Transactional
	public boolean saveAnnouncement(SpeakerDto speakerDto) {
		User user = userRepository.findByLoginId(speakerDto.getLoginId());
		try {
			Speaker speaker = Speaker.builder()
				.startTime(speakerDto.getStartTime())
				.endTime(speakerDto.getEndTime())
				.content(speakerDto.getContent())
				.user(user)
				.build();

			speakerRepository.save(speaker);
			return true;
		} catch (Exception e) {
			log.error("Failed to save announcement", e);
			return false;
		}
	}

	public List<String> getAllAnnouncements() {
		return speakerRepository.findAll().stream()
			.map(Speaker::getContent)
			.collect(Collectors.toList());
	}

	public SpeakerDto getAnnouncement() {
		return speakerRepository.findAll()
			.stream().filter(speaker -> speaker.getStartTime().isBefore(speaker.getEndTime()))
			.map(speaker -> {
				SpeakerDto speakerDto = new SpeakerDto();
				speakerDto.setContent(speaker.getContent());
				speakerDto.setStartTime(speaker.getStartTime());
				speakerDto.setEndTime(speaker.getEndTime());
				return speakerDto;
			})
			.findFirst().orElse(null);
	}
}

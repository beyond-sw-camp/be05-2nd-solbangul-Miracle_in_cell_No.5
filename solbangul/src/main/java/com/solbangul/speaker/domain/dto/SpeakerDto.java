package com.solbangul.speaker.domain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.solbangul.speaker.domain.Speaker;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpeakerDto {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
	private String content;
	private String loginId;

	public SpeakerDto() {
	}

	public SpeakerDto(Speaker speaker) {
		this.reservationDate = speaker.getStartTime().toLocalDate();
		this.reservationTime = speaker.getStartTime().toLocalTime();
		this.content = speaker.getContent();
	}
}

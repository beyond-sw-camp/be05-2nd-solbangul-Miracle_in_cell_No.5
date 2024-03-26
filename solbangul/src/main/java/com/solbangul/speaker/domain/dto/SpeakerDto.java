package com.solbangul.speaker.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerDto {
	private String content;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String loginId;
}

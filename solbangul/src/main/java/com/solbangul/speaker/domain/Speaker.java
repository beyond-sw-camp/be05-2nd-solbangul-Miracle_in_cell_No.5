package com.solbangul.speaker.domain;

import java.time.LocalDateTime;

import com.solbangul.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Speaker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "speaker_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@Column(name = "speaker_content", nullable = false)
	private String content;

	@Builder

	public Speaker(User user, LocalDateTime startTime, LocalDateTime endTime, String content) {
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
	}
}

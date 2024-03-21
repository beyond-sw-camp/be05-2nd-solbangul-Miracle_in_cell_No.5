package com.solbangul.hanwhauser.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "hanwha_user")
public class HanwhaUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hanwha_user_id")
	private Long id;

	@Column(name = "hanwha_user_name")
	private String name;

	@Column(name = "hanwha_user_git_email")
	private String gitEmail;
}

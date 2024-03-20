package com.solbangul.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.domain.dto.PostsSaveRequestDto;
import com.solbangul.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service("post")
@RequiredArgsConstructor
@Transactional(readOnly = true) // JPA는 Transaction 안에서만 동작
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postRepository.save(requestDto.toEntity()).getId();
	}

}

package com.solbangul.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;
import com.solbangul.post.domain.dto.PostEditRequestDto;
import com.solbangul.post.domain.dto.PostFindByRoomListResponseDto;
import com.solbangul.post.domain.dto.PostViewResponseDto;
import com.solbangul.post.domain.dto.PostsSaveRequestDto;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.room.domain.Room;
import com.solbangul.room.repository.RoomRepository;
import com.solbangul.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("post")
@RequiredArgsConstructor
@Transactional(readOnly = true) // JPA는 Transaction 안에서만 동작
@Slf4j
public class PostService {

	public static final int COMPLIMENT_AMOUNT = 3;
	private final PostRepository postRepository;
	private final RoomRepository roomRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		Room room = roomRepository.findById(requestDto.getRoomId()).orElseThrow();
		User user = room.getUser();

		Post post = requestDto.toEntity(room);
		if (post.getCategory().equals(Category.COMPLIMENT)) {
			user.addSolbangul(COMPLIMENT_AMOUNT);
		}
		return postRepository.save(post).getId();
	}

	// 한 회원의 방
	public PostViewResponseDto findById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		return new PostViewResponseDto(post);
	}

	public List<PostFindByRoomListResponseDto> findPostsByRoomId(Long id) {
		List<PostFindByRoomListResponseDto> postList = new ArrayList<>();
		List<Post> posts = postRepository.findPostsByRoomId(id);

		for (Post p : posts) {
			log.info("post 0 index post_id={}", p.getId());
			postList.add(new PostFindByRoomListResponseDto(p));
		}
		return postList;
	}

	public PostEditRequestDto editFindById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 post가 없습니다. id=" + id));

		return new PostEditRequestDto(post);
	}

	@Transactional
	public void update(Long id, PostEditRequestDto requestDto) {
		Post post = postRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));
		System.out.println("room id : " + post.getId());
		post.update(requestDto.getTitle(), requestDto.getPublicYn(), requestDto.getAnnonyYn(), requestDto.getContent(),
			requestDto.getCategory());
	}

	@Transactional
	public void delete(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));
		postRepository.delete(post);
	}

}

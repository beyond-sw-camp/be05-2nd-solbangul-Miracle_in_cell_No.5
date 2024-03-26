package com.solbangul.post.service;

import java.time.Duration;
import java.time.LocalDateTime;
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
import com.solbangul.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("post")
@RequiredArgsConstructor
@Transactional(readOnly = true) // JPA는 Transaction 안에서만 동작
@Slf4j
public class PostService {

	public static final int COMPLIMENT_AMOUNT = 3;
	public static final int WRITE_AMOUNT = 1;

	private final PostRepository postRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		Room receiverRoom = roomRepository.findById(requestDto.getRoomId()).orElseThrow();

		User receiverUser = receiverRoom.getUser();
		User senderUser = userRepository.findByNickname(requestDto.getWriter());

		Post post = requestDto.toEntity(receiverRoom);

		String writer = senderUser.getNickname();
		Category category = post.getCategory();
		Post lastPost = postRepository.findLastPost(writer, receiverRoom, category);

		if (postEmpty(lastPost)) {
			addSolbangulSenderAndReceiver(post, receiverUser, senderUser);
			return postRepository.save(post).getId();
		}
		LocalDateTime lastPostDateTime = lastPost.getCreatedDate();

		if (!isWithin24Hours(lastPostDateTime)) {
			addSolbangulSenderAndReceiver(post, receiverUser, senderUser);
		}

		return postRepository.save(post).getId();
	}

	private static boolean postEmpty(Post lastPost) {
		return lastPost == null;
	}

	private static void addSolbangulSenderAndReceiver(Post post, User receiverUser, User senderUser) {
		if (post.getCategory().equals(Category.COMPLIMENT)) {
			receiverUser.addSolbangul(COMPLIMENT_AMOUNT);
		}
		senderUser.addSolbangul(WRITE_AMOUNT);
	}

	private static boolean isWithin24Hours(LocalDateTime lastPostDateTime) {
		Duration duration = Duration.between(lastPostDateTime, LocalDateTime.now());
		long betweenHour = duration.toHours();
		return betweenHour < 24;
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
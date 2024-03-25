package com.solbangul.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.domain.Post;
import com.solbangul.room.domain.Room;
import com.solbangul.room.repository.RoomRepository;

@Transactional
@SpringBootTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;
	@Autowired
	RoomRepository roomRepository;

	@Test
	void findPostByRoomId() {
		Room targetRoom = createRoom("room1", "소개글1");
		Room anotherRoom = createRoom("room2", "소개글2");
		roomRepository.save(targetRoom);
		roomRepository.save(anotherRoom);

		Post post1 = createPost(targetRoom, "타이틀1");
		Post post2 = createPost(targetRoom, "타이틀2");
		Post post3 = createPost(anotherRoom, "타이틀3");
		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);

		List<Post> posts = postRepository.findPostsByRoomId(targetRoom.getId());
		assertThat(posts.size()).isEqualTo(2);
		assertThat(posts.get(0).getTitle()).isEqualTo(post1.getTitle());
		assertThat(posts.get(1).getTitle()).isEqualTo(post2.getTitle());
	}

	private Room createRoom(String name, String introduction) {
		return Room.builder()
			.roomName(name)
			.introduction(introduction)
			.build();
	}

	private static Post createPost(Room room, String title) {
		return Post.builder()
			.title(title)
			.room(room)
			.build();
	}
}
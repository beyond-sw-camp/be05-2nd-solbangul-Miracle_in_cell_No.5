package com.solbangul.post.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.dto.PostsSaveRequestDto;
import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.JoinRequestUserDto;
import com.solbangul.user.service.UserService;

@Transactional
@SpringBootTest
class PostServiceTest {

	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	@Test
	@DisplayName("사용자는 다른 사용자에게 칭찬글을 작성받을 경우 솔방울 3개를 지급 받고 보내는 사람은 솔방울 1개를 지급 받는다.")
	void incrementComplimentSolbangul() {
		JoinRequestUserDto senderUserDto = createJoinDto("보내는사람", "비밀번호", "보내는사람", "보내는사람", "이메일");
		Long senderUserId = userService.join(senderUserDto);

		JoinRequestUserDto receiverUserDto = createJoinDto("받는사람", "비밀번호", "받는사람", "받는사람", "이메일");
		Long receiverUserId = userService.join(receiverUserDto);

		User receiverUser = userService.findOne(receiverUserId);
		Long receiverRoomId = receiverUser.getRoom().getId();

		User senderUser = userService.findOne(senderUserId);

		PostsSaveRequestDto postDto = createPostDto(receiverRoomId, Category.COMPLIMENT, "내용3",
			senderUser.getNickname());
		postDto.setRoomId(receiverRoomId);
		postDto.setUserId(senderUserId);

		postService.save(postDto);

		assertThat(userService.findOne(receiverUserId).getSolbangul()).isEqualTo(3);
		assertThat(userService.findOne(senderUserId).getSolbangul()).isEqualTo(1);
	}

	@Test
	@DisplayName("사용자는 다른 사용자에게 건의글을 작성받을 경우는 솔방울을 지급받지 않고 받는 사람만 솔방울 1개를 지급 받는다.")
	void claimsSolbangul() {
		JoinRequestUserDto senderUserDto = createJoinDto("보내는사람", "비밀번호", "보내는사람", "보내는사람", "이메일");
		Long senderUserId = userService.join(senderUserDto);

		JoinRequestUserDto receiverUserDto = createJoinDto("받는사람", "비밀번호", "받는사람", "받는사람", "이메일");
		Long receiverUserId = userService.join(receiverUserDto);

		User receiverUser = userService.findOne(receiverUserId);
		Long receiverRoomId = receiverUser.getRoom().getId();

		User senderUser = userService.findOne(senderUserId);

		PostsSaveRequestDto postDto = createPostDto(receiverRoomId, Category.CLAIMS, "내용3", senderUser.getNickname());
		postDto.setRoomId(receiverRoomId);
		postDto.setUserId(senderUserId);

		postService.save(postDto);

		assertThat(userService.findOne(receiverUserId).getSolbangul()).isEqualTo(0);
		assertThat(userService.findOne(senderUserId).getSolbangul()).isEqualTo(1);
	}

	@Test
	@DisplayName("같은 대상에게 같은 사람이 오늘 안으로 글을 작성했다면 solbangul이 보내는 사람 받는 사람 둘 다 오르지 않아야 한다.")
	void addSolbangul24Hour() {

	}

	private static PostsSaveRequestDto createPostDto(Long roomId, Category category, String content, String writer) {
		PostsSaveRequestDto postDto = new PostsSaveRequestDto();
		postDto.setCategory(category);
		postDto.setRoomId(roomId);
		postDto.setContent(content);
		postDto.setWriter(writer);
		return postDto;
	}

	private static JoinRequestUserDto createJoinDto(String loginId, String password, String name, String nickname,
		String email) {
		JoinRequestUserDto joinRequestUserDto = new JoinRequestUserDto();
		joinRequestUserDto.setLoginId(loginId);
		joinRequestUserDto.setPassword(password);
		joinRequestUserDto.setName(name);
		joinRequestUserDto.setNickname(nickname);
		joinRequestUserDto.setEmail(email);
		return joinRequestUserDto;
	}
}
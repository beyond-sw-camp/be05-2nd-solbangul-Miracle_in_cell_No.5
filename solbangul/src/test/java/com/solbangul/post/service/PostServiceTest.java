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
	@DisplayName("사용자는 다른 사용자에게 칭찬글을 작성받을 경우 솔방울 3개를 지급 받는다.")
	void incrementComplimentSolbangul() {
		JoinRequestUserDto joinRequestUserDto = createJoinDto();
		Long userId = userService.join(joinRequestUserDto);

		User findUser = userService.findOne(userId);
		Long roomId = findUser.getRoom().getId();

		PostsSaveRequestDto postDto = new PostsSaveRequestDto();
		postDto.setCategory(Category.COMPLIMENT);
		postDto.setRoomId(roomId);
		postDto.setContent("내용");

		postService.save(postDto);

		assertThat(userService.findOne(userId).getSolbangul()).isEqualTo(3);
	}

	@Test
	@DisplayName("사용자는 다른 사용자에게 건의글을 작성받을 경우는 솔방울을 지급받지 않는다.")
	void notIncrementClaimsSolbangul() {
		JoinRequestUserDto joinRequestUserDto = createJoinDto();
		Long userId = userService.join(joinRequestUserDto);

		User findUser = userService.findOne(userId);
		Long roomId = findUser.getRoom().getId();

		PostsSaveRequestDto postDto1 = new PostsSaveRequestDto();
		postDto1.setCategory(Category.CLAIMS);
		postDto1.setRoomId(roomId);
		postDto1.setContent("내용");

		postService.save(postDto1);
		
		assertThat(userService.findOne(userId).getSolbangul()).isEqualTo(0);
	}

	@Test
	void name() {
	}

	private static JoinRequestUserDto createJoinDto() {
		JoinRequestUserDto joinRequestUserDto = new JoinRequestUserDto();
		joinRequestUserDto.setLoginId("아이디");
		joinRequestUserDto.setPassword("비밀번호");
		joinRequestUserDto.setName("이름");
		joinRequestUserDto.setNickname("닉네임");
		joinRequestUserDto.setEmail("이메일");
		return joinRequestUserDto;
	}
}
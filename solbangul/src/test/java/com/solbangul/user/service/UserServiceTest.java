package com.solbangul.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solbangul.user.domain.User;
import com.solbangul.user.domain.dto.JoinRequestUserDto;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	void baseEntity() throws InterruptedException {
		JoinRequestUserDto joinRequestUserDto = new JoinRequestUserDto();
		joinRequestUserDto.setName("user");
		joinRequestUserDto.setEmail("test@mail.com");
		joinRequestUserDto.setLoginId("login");
		joinRequestUserDto.setPassword("password");
		joinRequestUserDto.setPasswordConfirm("password");
		joinRequestUserDto.setNickname("nick");

		Long id = userService.join(joinRequestUserDto);
		Thread.sleep(1000);
		userService.updateUser(id, "updateName");
		User find = userService.findOne(id);
		System.out.println("find.getCreatedDate() = " + find.getCreatedDate());
		System.out.println("find.getModifiedDate() = " + find.getModifiedDate());
	}
}
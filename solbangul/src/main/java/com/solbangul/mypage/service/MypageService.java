package com.solbangul.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.solbangul.mypage.domain.UpdateUserInfoDto;
import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;
import com.solbangul.user.domain.User;
import com.solbangul.user.repository.UserRepository;

@Service
public class MypageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PostRepository postRepository;



    @Transactional(readOnly = true)
    public User getMypageInfo(String username) {
        return userRepository.findByLoginId(username);
    }

    @Transactional
    public void updateUserInfo(UpdateUserInfoDto updateUserInfoDto, String username) {
        User user = userRepository.findByLoginId(username);
        if (user != null) {
            if (updateUserInfoDto.getNickname() != null && !updateUserInfoDto.getNickname().isEmpty()) {
                user.setNickname(updateUserInfoDto.getNickname());
            }
            if (updateUserInfoDto.getPassword() != null && !updateUserInfoDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updateUserInfoDto.getPassword()));
            }
            // 프로필 사진 변경 로직---> 이거잘 모르겠움...
            if (updateUserInfoDto.getProfileImage() != null && !updateUserInfoDto.getProfileImage().isEmpty()) {
                
            }
            userRepository.save(user);
        }
    }

    public List<Post> getMyPosts(String writer) {

        return postRepository.findAllByWriter(writer);
    }
}
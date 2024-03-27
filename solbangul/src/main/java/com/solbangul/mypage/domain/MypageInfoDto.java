package com.solbangul.mypage.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MypageInfoDto {
    private String profileImage;
    private String nickname;
    private String name;
    private String gitEmail;

}

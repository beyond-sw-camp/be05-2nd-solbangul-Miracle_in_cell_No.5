package com.solbangul.mypage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.solbangul.mypage.domain.UpdateUserInfoDto;
import com.solbangul.mypage.service.MypageService;
import com.solbangul.post.domain.Post;
import com.solbangul.user.domain.dto.CustomUserDetails;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    @Autowired
    private MypageService mypageService;


    @GetMapping
    public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
        return "mypage";
    }

    @GetMapping("/edit")
    public String editUserInfo(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        model.addAttribute("user", mypageService.getMypageInfo(currentUser.getUsername()));
        return "edit_user_info";
    }

    @PostMapping("/update")
    public String updateUserInfo(UpdateUserInfoDto updateUserInfoDto, @AuthenticationPrincipal CustomUserDetails currentUser) {
        mypageService.updateUserInfo(updateUserInfoDto, currentUser.getUsername());
        return "redirect:/mypage";
    }

    @GetMapping("/myposts")
    public String getMyPosts(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Post> myPosts = mypageService.getMyPosts(currentUser.getUsername());
        model.addAttribute("myPosts", myPosts);
        return "myposts";
    }
}
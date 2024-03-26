package com.solbangul.admin.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;

import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PostRepository postRepository;

    
    @GetMapping
    public String viewAllPosts(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {

        List<Post> allposts = postRepository.findAll();
        model.addAttribute("posts", allposts);
        
        return "view_admin";
    }
    

}

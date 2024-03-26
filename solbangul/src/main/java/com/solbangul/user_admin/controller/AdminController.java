package com.solbangul.user_admin.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.solbangul.post.domain.Post;
import com.solbangul.post.repository.PostRepository;

import com.solbangul.user.domain.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class AdminController {

    private final PostRepository postRepository;


    
    @GetMapping("/admin")
    public String viewAllPosts(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {

        List<Post> allposts = postRepository.findAll();
        model.addAttribute("posts", allposts);
        
        return "admin";
    }
    
    @PostMapping("/admin/delete")
    public String deleteByAdmin(@RequestParam("postId") Long postId) {
        postRepository.deleteById(postId);
        return "redirect:/admin";
    }

}

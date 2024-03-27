package com.solbangul.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostService postService;
    
    @Transactional
    public void DeleteByAdmin(Long id){
        postService.delete(id);
    }
}

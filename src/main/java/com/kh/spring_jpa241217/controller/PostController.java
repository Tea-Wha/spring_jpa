package com.kh.spring_jpa241217.controller;

import com.kh.spring_jpa241217.dto.request.SavePostRequest;
import com.kh.spring_jpa241217.dto.response.ApiResponse;
import com.kh.spring_jpa241217.dto.response.PostInfoResponse;
import com.kh.spring_jpa241217.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostInfoResponse>> handleSavePost(@RequestBody SavePostRequest requestDto) {
        PostInfoResponse responseDataDto = postService.save(requestDto);
        ApiResponse<PostInfoResponse> response = new ApiResponse<>(true, "게시글 등록 성공", responseDataDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

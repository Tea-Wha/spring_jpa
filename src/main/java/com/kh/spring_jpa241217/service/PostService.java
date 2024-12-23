package com.kh.spring_jpa241217.service;

import com.kh.spring_jpa241217.dto.request.SavePostRequest;
import com.kh.spring_jpa241217.dto.response.PostInfoResponse;
import com.kh.spring_jpa241217.entity.Post;
import com.kh.spring_jpa241217.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    public PostInfoResponse save(SavePostRequest dto) {
        Post post = new Post();
        post.setBoard(boardService.getByName(dto.getBoardName()));
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setMember(memberService.getById(dto.getMemberId()));
        postRepository.save(post);

        return convertToPostInfoResponse(post);
    }

    private PostInfoResponse convertToPostInfoResponse(Post post) {
        return PostInfoResponse.builder()
            .boardName(post.getBoard().getName())
            .memberId(post.getMember().getId())
            .postId(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .publishedAt(post.getPublishedAt())
            .updatedAt(post.getUpdatedAt())
            .build();
    }
}

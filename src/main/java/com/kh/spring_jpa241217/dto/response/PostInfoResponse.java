package com.kh.spring_jpa241217.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInfoResponse {
    private String boardName;
    private Long memberId;
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}

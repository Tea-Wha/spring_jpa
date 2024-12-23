package com.kh.spring_jpa241217.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavePostRequest {
    private String boardName;
    private Long memberId;
    private String title;
    private String content;
}

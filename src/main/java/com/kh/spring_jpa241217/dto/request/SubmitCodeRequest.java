package com.kh.spring_jpa241217.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitCodeRequest {
    private String language;
    private String code;
}

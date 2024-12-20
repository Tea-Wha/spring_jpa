package com.kh.spring_jpa241217.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}

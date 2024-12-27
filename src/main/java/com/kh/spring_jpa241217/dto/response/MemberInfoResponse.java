package com.kh.spring_jpa241217.dto.response;

import com.kh.spring_jpa241217.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoResponse {
    private Long memberId;
    private String email;
    private String name;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    public static MemberInfoResponse of(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .registeredAt(member.getRegisteredAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}

package com.kh.spring_jpa241217.dto.request;

import com.kh.spring_jpa241217.constant.Authority;
import com.kh.spring_jpa241217.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveMemberRequest {
    private String email;
    private String password;
    private String name;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .authority(Authority.ROLE_USER)
                .build();
    }
}

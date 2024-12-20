package com.kh.spring_jpa241217.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveMemberRequest {
    private String email;
    private String password;
    private String name;
}

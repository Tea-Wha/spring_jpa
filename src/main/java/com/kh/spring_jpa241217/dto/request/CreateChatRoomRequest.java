package com.kh.spring_jpa241217.dto.request;

import lombok.Data;

@Data
public class CreateChatRoomRequest {
    private String memberId;
    private String name;
}

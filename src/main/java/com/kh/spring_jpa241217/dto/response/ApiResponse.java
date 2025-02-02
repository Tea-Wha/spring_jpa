package com.kh.spring_jpa241217.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse (String message) {
        this.success = false;
        this.message = message;
        this.data = null;
    }
}

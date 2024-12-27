package com.kh.spring_jpa241217.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@NoArgsConstructor
public class ChatRoomInfoResponse {
    private String roomId;
    private String name;
    private LocalDateTime regDate;

    @JsonIgnore // 직렬화 방지
    private Set<WebSocketSession> sessions; // 채팅에 참여 중인 세션 정보

    public boolean isSessionEmpty() {return this.sessions.isEmpty();}

    @Builder // 만약 클래스 수준에서 적용하면 존재하는 모든 생성자 형태의 빌더 패턴 지원
    public ChatRoomInfoResponse(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
        this.regDate = LocalDateTime.now();

        // Set은 'tread-safe' 하지 않기 때문에 예외가 발생합니다.
        // 이를 해결하기 위해서 ConcurrentHashMap의 Key로 부터 Set 객체를 생성
        // Set을 thread-safe로 쓰기 위한 가장 좋은 방법임 (병렬 처리 가능, 세밀한 lock 단위를 사용하여 반복 작업에서 thread-safe 보장 등)
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
}
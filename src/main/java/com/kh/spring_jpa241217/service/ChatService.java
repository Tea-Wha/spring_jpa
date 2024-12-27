package com.kh.spring_jpa241217.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring_jpa241217.dto.ChatMessage;
import com.kh.spring_jpa241217.dto.response.ChatRoomInfoResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomInfoResponse> chatRooms;

    @PostConstruct // 의존성 주입 이후에 초기화를 수행하는 메서드
    private void init() {
        chatRooms = new LinkedHashMap<>(); // 순서 보장하는 해시맵
    }

    public List<ChatRoomInfoResponse> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomInfoResponse findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoomInfoResponse createChatRoom(String name) {
        // UUID는 UNIQUE가 보장되는 임의의 값
        String roomId = UUID.randomUUID().toString();
        log.info("UUID : {}", roomId);
        ChatRoomInfoResponse chatRoom = ChatRoomInfoResponse.builder()
            .roomId(roomId)
            .name(name)
            .build();
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    public ChatRoomInfoResponse deleteChatRoom(String roomId) {
        ChatRoomInfoResponse chatRoom = chatRooms.get(roomId);
        if (chatRoom != null) {
            if (chatRoom.isSessionEmpty()) chatRooms.remove(roomId);
            return chatRoom;
        }
        return null; // 또는 예외
    }

    public void handleSessionEnter(String roomId, WebSocketSession newSession, ChatMessage chatMessage) {
        ChatRoomInfoResponse chatRoom = this.findRoomById(roomId);
        if (chatRoom != null) {
            chatRoom.getSessions().add(newSession);
            if (chatMessage.getSender() != null) {
                chatMessage.setMessage(chatMessage.getMessage() + "님이 입장하셨습니다.");
                for (WebSocketSession session : chatRoom.getSessions()) {
                    sendMessage(session, chatMessage);
                }
            }
            log.info("새로운 세션이 {}번 채팅방에 추가되었습니다. 세션 정보 : {}", roomId, newSession);
        }
    }

    public void handleSessionLeave(String roomId, WebSocketSession leavingSession, ChatMessage chatMessage) {
        ChatRoomInfoResponse chatRoom = this.findRoomById(roomId);
        if (chatRoom != null) {
            chatRoom.getSessions().remove(leavingSession);

        }
    }

    public void sendMessage(WebSocketSession session, ChatMessage chatMessage) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } catch (IOException ex) {
            // void error(String var1, Throwable var2);
            log.error(ex.getMessage(), ex);
        }
    }
}

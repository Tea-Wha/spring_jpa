package com.kh.spring_jpa241217.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring_jpa241217.dto.ChatMessage;
import com.kh.spring_jpa241217.service.ChatService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
    }
}

@RequiredArgsConstructor
@Slf4j
@Component // Spring Container에 빈 등록, Service, Repository 등의 최상위 인터페이스
class WebSocketHandler extends TextWebSocketHandler {
    // JSON 데이터를 직렬화 역직렬화 하기 위해 사용, RestController 등에서는 자동으로 수행해줬던 것임
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    // thread-safe한 hashmap
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);

        // JSON 문자열을 ChatMessage DTO로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        String roomId = chatMessage.getRoomId();
        if (chatMessage.getType() == ChatMessage.MessageType.ENTER) {
            sessionRoomIdMap.put(session, roomId);
            chatService.handleSessionEnter(roomId, session, chatMessage);
        } else if (chatMessage.getType() == ChatMessage.MessageType.LEAVE) {
            sessionRoomIdMap.remove(session);
            chatService.handleSessionLeave(roomId, session, chatMessage);
        } else {
            for (var _session: chatService.findRoomById(roomId).getSessions()) {
                chatService.sendMessage(_session, chatMessage);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("연결 해제 이후 동작 : {}", session);
        String roomId = sessionRoomIdMap.remove(session);
        if (roomId != null) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatService.handleSessionLeave(roomId, session, chatMessage);
        }
    }
}

package com.kh.spring_jpa241217.controller;

import com.kh.spring_jpa241217.dto.request.CreateChatRoomRequest;
import com.kh.spring_jpa241217.dto.response.ChatRoomInfoResponse;
import com.kh.spring_jpa241217.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/room/create")
    public ResponseEntity<String> createRoom(@RequestBody CreateChatRoomRequest requestData) {
        log.info("CreateChatRoomRequest DTO : {}", requestData);
        ChatRoomInfoResponse chatRoom = chatService.createChatRoom(requestData.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoom.getRoomId());
    }

    @GetMapping("/room")
    public List<ChatRoomInfoResponse> findAllRoom() {
        return chatService.findAllRoom();
    }

    @GetMapping("/room/{roomId}")
    public ChatRoomInfoResponse findRoomById(@PathVariable String roomId) {
        return chatService.findRoomById(roomId);
    }
}

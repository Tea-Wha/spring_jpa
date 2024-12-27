package com.kh.spring_jpa241217.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kh.spring_jpa241217.dto.response.ApiResponse;
import com.kh.spring_jpa241217.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> handleCreateNewBoard(@RequestBody ObjectNode requestData) {
        String createdBoardName = boardService.save(requestData.get("boardName").asText());
        Map<String, String> responseData = new HashMap<>();
        responseData.put("boardName", createdBoardName);

        ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "게시판 등록 성공", responseData);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Boolean> test() {
        boardService.test();
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}

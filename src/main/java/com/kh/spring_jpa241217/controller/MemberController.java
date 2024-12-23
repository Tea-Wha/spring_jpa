package com.kh.spring_jpa241217.controller;

import com.kh.spring_jpa241217.dto.request.SaveMemberRequest;
import com.kh.spring_jpa241217.dto.response.ApiResponse;
import com.kh.spring_jpa241217.dto.response.MemberInfoResponse;
import com.kh.spring_jpa241217.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberInfoResponse>> handleSignUp(@RequestBody SaveMemberRequest requestDto) {
        MemberInfoResponse responseDataDto = memberService.save(requestDto);
        ApiResponse<MemberInfoResponse> response = new ApiResponse<>(true, "회원가입 성공", responseDataDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberInfoResponse>>> handleGetAllMembers() {
        List<MemberInfoResponse> responseDataDtoList = memberService.getMemberInfoAll();
        ApiResponse<List<MemberInfoResponse>> response = new ApiResponse<>(true, "모든 회원정보 조회 결과", responseDataDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // exists 정보는 프론트에서 HEAD로 보내서 ok(200) not found(404) 인지를 확인하여 처리가 가능합니다.
    // HEAD 메서드로 요청하는 경우 백엔드는 GET으로 매핑하여 처리합니다.
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> handleGetMemberById(@PathVariable Long id) {
        MemberInfoResponse responseDataDto = memberService.getMemberInfo(id);
        ApiResponse<MemberInfoResponse> response = new ApiResponse<>(true, "회원정보 조회 결과", responseDataDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 실제로는 토큰이 인증된 사용자로 제한해야 합니다.
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> handleUpdateMember(@PathVariable Long id, @RequestBody SaveMemberRequest requestDto) {
        MemberInfoResponse responseDataDto = memberService.update(id, requestDto);
        ApiResponse<MemberInfoResponse> response = new ApiResponse<>(true, "회원 정보 수정 성공", responseDataDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 실제로는 토큰이 인증된 사용자로 제한해야 합니다.
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> handleDeleteMember(@PathVariable Long id) {
        MemberInfoResponse responseDataDto = memberService.delete(id);
        ApiResponse<MemberInfoResponse> response = new ApiResponse<>(true, "회원 삭제 성공", responseDataDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    // 빌더란? 객체 생성을 단순화 하는 패턴으로 Lombok에서 지원하는 것으로 보임
}
package com.kh.spring_jpa241217.service;

import com.kh.spring_jpa241217.dto.request.LoginRequest;
import com.kh.spring_jpa241217.entity.Member;
import com.kh.spring_jpa241217.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final MemberRepository memberRepository;

    // 로그인
    // 추후 토큰 발행 로직으로 변경
    public boolean login(LoginRequest dto) {
        try {
            Optional<Member> member = memberRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
            return member.isPresent();
        } catch (Exception e) {
            log.error("로그인 검증 실패 : {}", e.getMessage());
            return false;
        }
    }
}

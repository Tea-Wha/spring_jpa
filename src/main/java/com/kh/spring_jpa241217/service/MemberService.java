package com.kh.spring_jpa241217.service;

import com.kh.spring_jpa241217.dto.request.SaveMemberRequest;
import com.kh.spring_jpa241217.dto.response.MemberInfoResponse;
import com.kh.spring_jpa241217.entity.Member;
import com.kh.spring_jpa241217.exception.DataNotFoundException;
import com.kh.spring_jpa241217.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    // Autowired 같은 경우, RequiredArgsConstructor가 있기 떄문에,
    // 생성자에서 의존성 주입을 받으므로 생략 가능
    private final MemberRepository memberRepository;

    public MemberInfoResponse save(Long id, SaveMemberRequest requestDto) {
        Member member;
        if (id != null) {
            member = memberRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("해당 회원을 찾을 수 없습니다. 회원 식별자 id 값 : " + id));
            member.setUpdatedAt(LocalDateTime.now());
            if (requestDto.getPassword() != null) {
                member.setPassword(requestDto.getPassword());
            }
        } else {
            member = new Member();
            member.setPassword(requestDto.getPassword());
        }

        member.setEmail(requestDto.getEmail());
        member.setName(requestDto.getName());
        memberRepository.save(member);

        return convertToMemberInfoResponse(member);
    }

    public List<MemberInfoResponse> getMemberInfoAll() {
        return memberRepository.findAll().stream()
                .map(this::convertToMemberInfoResponse)
                .collect(Collectors.toList());
    }

    public MemberInfoResponse getMemberInfo(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원을 찾을 수 없습니다. 회원 식별자 id 값 : " + id));
        return convertToMemberInfoResponse(member);
    }

    public MemberInfoResponse delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원을 찾을 수 없습니다. 회원 식별자 id 값 : " + id));
        memberRepository.delete(member);
        return convertToMemberInfoResponse(member);
    }

    private MemberInfoResponse convertToMemberInfoResponse(Member member) {
        return new MemberInfoResponse(
            member.getId(),
            member.getEmail(),
            member.getName(),
            member.getRegisteredAt(),
            member.getUpdatedAt()
        );
    }
}

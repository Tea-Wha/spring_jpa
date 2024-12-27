package com.kh.spring_jpa241217.repository;

import com.kh.spring_jpa241217.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // JpaRepository를 상속함으로써 기본적인 데이터 CRUD는 이미 가능
    // 아래 custom 메서드는 작명 규칙에 따라 정의 되어야 함
     boolean existsByEmail(String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);

    /**
     * 현재 인터페이스가 인터페이스 메서드를 Override하는 목적은 타입을 구체화하기 위함이다..
     *
     * public interface BaseRepository<T> {
     *     void save(T entity);
     * }
     *
     * public interface UserRepository extends BaseRepository<User> {
     *     @Override
     *     void save(User user); // 구체적인 타입으로 재선언
     * }
     * */
}

/**
 * Optional<Member> memberOpt = memberRepository.findByEmail("example@domain.com");
 *
 * // 안전하게 처리
 * memberOpt.ifPresent(member -> System.out.println(member.getName()));
 *
 * // 기본값 제공
 * Member member = memberOpt.orElse(new Member());
 *
 * // 예외 던지기
 * Member member2 = memberOpt.orElseThrow(() -> new RuntimeException("Member not found"));
 * */

/**
 * Optional.empty() 는 최종 응답에서 null 문자열로 치환된다.
 * */

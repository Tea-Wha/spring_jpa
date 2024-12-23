package com.kh.spring_jpa241217.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class) // 시간 정보를 어노테이션으로 처리하기 위해 필요
@NoArgsConstructor
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 100, message = "게시글 제목은 1자 이상, 100자 이하여야 합니다.")
    private String title;

    @Lob
    @Column(length=1000)
    @Size(min = 1, max = 1000, message = "게시판 내용은 1자 이상, 1000자 이하여야 합니다.")
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime publishedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 실제 이 컬럼(필드)를 사용할 때 추가적인 쿼리가 실행되어 값을 로드 => 자주 사용되는데 lazy를 쓰면 오히려 병목
    // FetchType.EAGER 설정: 연관된 Post 엔티티는 부모 엔티티가 로드될 때 즉시 조인(join)을 통해 함께 로드됩니다. 따라서, 항상 조인된 상태로 인스턴스가 생성됩니다.
    // 지연로딩을 너무 자주 쓰면 N+1 문제가 발생할 수 있다하는데 이것은 조사해볼것
    // @ManyToOne은 (fetch = FetchType.EAGER)가 기본 값임
    @ManyToOne
    @JoinColumn(name = "member_id") // name은 스키마에 실제 저장되는 FK의 이름을 의미한다. 직접 설정해주는 편이 속편하다.
    private Member member;

    // post (n)-----(1) board
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}

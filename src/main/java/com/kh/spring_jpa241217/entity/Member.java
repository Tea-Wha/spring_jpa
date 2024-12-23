package com.kh.spring_jpa241217.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 기본적으로 식별자명을 snake case로 변환
// 아래는 명시적 이름 할당으로 생략가능
@Table(name="member", uniqueConstraints = {
    @UniqueConstraint(name = "unique_email", columnNames = "email"),
})
@Entity
@EntityListeners(AuditingEntityListener.class) // 시간 정보를 어노테이션으로 처리하기 위해 필요
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    // 생성타입은 TABLE(, SEQUENCE(시퀀스 생성), IDENTITY(DB에 위임, MySQL의 경우 AUTO_INCREMENT)가 있다.
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    // 명시적 컬럼명 정의
    @Column(name = "member_id")
    private Long id; // PK

    @Column(nullable = false, unique = true, length = 50)
    @Size(min = 5, max = 50, message = "이메일은 5자 이상, 50자 이하여야 합니다.")
    private String email;

    @Column(nullable = false, length = 50)
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상, 50자 이하여야 합니다.")
    private String password;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50, message = "이름은 1자 이상, 50자 이하여야 합니다.")
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false) // 최초 생성 이후 update X
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;



//    // JPA의 콜백 메서드,
//    // JPA Entity 라이프 사이클에서 persist 상태로 변하기 전에 실행됨
//    // repository save 메서드 호출시 persist 상태가 되며, 해당 어노테이션은 신규 데이터 삽입에만 적용됨
//    @PrePersist
//    protected void beforeCreate() {
//        this.registeredAt = LocalDateTime.now();
//    }
}

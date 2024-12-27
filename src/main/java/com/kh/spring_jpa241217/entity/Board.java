package com.kh.spring_jpa241217.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(uniqueConstraints = {
    @UniqueConstraint(name = "unique_name", columnNames = "name"),
})
@Entity
@Data
@NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 50, message = "게시판 이름은 1자 이상, 50자 이하여야 합니다.")
    private String name;

    // CASCADE는 영속성을 전이하는 설정으로
    // 부모 엔티티의 변화를 자식 엔티티에 반영하는 방법이다.
    // 즉, 부모 엔티티에서 자식 엔티티를 관리할 때 편리한 기법이지만 위험할 수 있다.
    // orphanRemoval = true 는 고아객체를 제거하는 것으로, 이 리스트에서 자식이 제거되거나
    // 참조가 제거되면 해당 자식 엔티티는 **영속성 컨텍스트에서 제거(dirty check)**되며 데이터베이스에서 자동으로 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
}

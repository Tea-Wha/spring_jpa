package com.kh.spring_jpa241217.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

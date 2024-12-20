package com.kh.spring_jpa241217.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    public enum ItemSellStatus {
        SELL, SOLD_OUT
    }

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)

    // 1) 어노테이션이 없거나,
    // 2) @Enumerated만 쓰는 경우,
    // 3) EnumType.ORDINAL은 모두 동일하게 동작하며, 이 경우 ordinal 값이 DB 레코드 값으로 저장된다.
    @Enumerated(EnumType.STRING) // STRING은 enum 객체 name 반환 값에 대응한다.
    private ItemSellStatus itemSellStatus;

    @Lob // 대용량 데이터 매핑, LENGTH에 따라 TEXT 종류가 바뀐다. 명시적으로 지정하려면 아래 참조
    @Column(nullable = false, columnDefinition="LoNgTeXt")
    private String itemDetail;  // 상품 상세 설명

    @Column(nullable = false)
    private Integer price; // 가격
}

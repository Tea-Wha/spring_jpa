package com.kh.spring_jpa241217.repository;

import com.kh.spring_jpa241217.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ItemRepository {
//    List<Item> findByItemName(String itemName);
//
//    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
//
//    List<Item> findByPriceLessThan(int price);
//
//    List<Item> findByPriceGreaterThanEqualAndItemSellStatus(int price, Item.ItemSellStatus itemSellStatus);
//
//    List<Item> findAllByOrderByPriceDesc();
//
//    List<Item> findByItemNameContaining(String itemName);
//
//    List<Item> findByItemNameAndPriceEquals(String itemName, int price);
//
//    List<Item> findByPriceBetween(int biggerThan, int smallerThan);
//
//    // JPQL(JAVA Persistence Query Language) : 객체 지향 쿼리 언어, 테이블이 아닌 엔티티를 대상으로 작성
//    // 콜론으로 값을 내부 쿼리문에 포매팅 가능
//    @Query("SELECT i FROM Item i WHERE i.itemDetail LIKE %:yesItemDetail% ORDER BY i.price DESC")
//    List<Item> findByItemDetail(@Param("yesItemDetail") String itemDetail);
//
//    // nativeQuery
//    @Query(value = "SELECT * FROM item i WHERE i.item_detail LIKE %:yesItemDetail% ORDER BY i.price DESC", nativeQuery = true)
//    List<Item> findByItemDetailNative(@Param("yesItemDetail") String itemDetail);
}

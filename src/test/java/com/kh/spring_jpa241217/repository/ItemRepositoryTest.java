package com.kh.spring_jpa241217.repository;

import com.kh.spring_jpa241217.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void 상품만들기() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(100*i + 100);
            item.setItemSellStatus(Item.ItemSellStatus.SELL);
            item.setItemDetail("테스트 상품 상세" + i);
            Item savedItem = itemRepository.save(item);
            log.info("저장된 상품 : {}", savedItem.toString());
        }
    }

    @Test
    @DisplayName("상품 이름 일치 조회 테스트")
    public void 상품_이름_일치_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품1");
        for (var item: itemList) log.info("\"테스트 상품1\"과 이름이 일치하는 상품 : {}", item);
    }

    @Test
    @DisplayName("상품 이름 또는 상세 일치 조회 테스트")
    public void 상품_이름_또는_상세_일치_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세2");
        for (var item: itemList) log.info("\"테스트 상품1\"과 이름이 일치 또는 \"테스트 상품 상세2\"와 상세가 일치 하는 상품 : {}", item);
    }

    @Test
    @DisplayName("상품 가격이 특정 가격 미만인 상품 조회 테스트")
    public void 상품_가격이_특정_가격_미만인_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByPriceLessThan(500);
        for (var item: itemList) log.info("\"500\"원 미만의 상품 : {}", item);
    }

    @Test
    @DisplayName("상품 가격이 특정 가격 이상이고 SELL 상태인 상품 조회 테스트")
    public void 상품_가격이_특정_가격_이상이고_SELL_상태인_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByPriceGreaterThanEqualAndItemSellStatus(500, Item.ItemSellStatus.SELL);
        for (var item: itemList) log.info("\"500\"원 이상이고 \"SELL\" 상태인 상품 : {}", item);
    }

    @Test
    @DisplayName("상품 목록 가격을 기준으로 내림차순 정렬하여 조회 테스트")
    public void 상품_목록_가격을_기준으로_내림차순_정렬하여_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findAllByOrderByPriceDesc();
        for (var item: itemList) log.info("\"가격\"을 기준으로 내림차순 된 상품 목록 : {}", item);
    }

    @Test
    @DisplayName("상품 이름이 특정 문자열을 포함한 상품 조회 테스트")
    public void 상품_이름이_특정_문자열을_포함한_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemNameContaining("5");
        for (var item: itemList) log.info("\"5\"를 이름에 포함한 상품 : {}", item);
    }

    @Test
    @DisplayName("상품 이름과 가격이 일치하는 상품 조회 테스트")
    public void 상품_이름과_가격이_일치하는_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemNameAndPriceEquals("테스트 상품1", 200);
        for (var item: itemList) log.info("이름이 \"테스트 상품1\"이고 가격이 \"200\" 원인 상품 목록 : {}", item);
    }

    @Test
    @DisplayName("특정 가격대 범위의 상품 조회 테스트")
    public void 특정_가격대_범위의_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByPriceBetween(200, 600);
        for (var item: itemList) log.info("가격이 \"200\" 원 이상 \"600원\"이하인 상품 목록 : {}", item);
    }

    @Test
    @DisplayName("상품 상세에 특정 문자열을 포함하는 상품 조회 테스트")
    public void 상품_상세에_특정_문자열을_포함하는_상품_조회_테스트() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemDetail("1");
        for (var item: itemList) log.info("\"1\"이 상품 상세에 포함된 상품 목록 : {}", item);
    }

    @Test
    @DisplayName("상품 상세에 특정 문자열을 포함하는 상품 조회 테스트 using 네이티브 쿼리")
    public void 상품_상세에_특정_문자열을_포함하는_상품_조회_테스트_using_네이티브_쿼리() {
        this.상품만들기();
        List<Item> itemList = itemRepository.findByItemDetailNative("1");
        for (var item: itemList) log.info("\"1\"이 상품 상세에 포함된 상품 목록 : {}", item);
    }
}
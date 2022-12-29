package com.shop.projectlion.domain.item.entity;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    @DisplayName("상품 업데이트 테스트")
    void updateItem() {
        // given
        Item item = Item.builder()
                .itemName("상품명")
                .itemSellStatus(ItemSellStatus.SOLD_OUT)
                .itemDetail("상품 상세")
                .price(5000)
                .stockNumber(50)
                .build();

        Item updateItem = Item.builder()
                .itemName("업데이트 상품명")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("업데이트 상품 상세")
                .price(1000)
                .stockNumber(100)
                .build();

        // when
        item.updateItem(updateItem);

        // then
        Assertions.assertThat(item.getItemName()).isEqualTo(updateItem.getItemName());
        Assertions.assertThat(item.getItemSellStatus()).isEqualTo(updateItem.getItemSellStatus());
        Assertions.assertThat(item.getItemDetail()).isEqualTo(updateItem.getItemDetail());
        Assertions.assertThat(item.getPrice()).isEqualTo(updateItem.getPrice());
        Assertions.assertThat(item.getStockNumber()).isEqualTo(updateItem.getStockNumber());
    }


}
package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.UpdateItemDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ApiItemServiceTest {

    @InjectMocks
    private ApiItemService apiItemService;

    @Mock
    private ItemService itemService;

    @Mock
    private DeliveryService deliveryService;

    @Test
    @DisplayName("상품 업데이트 테스트")
    void updateItem() {

        // given
        Long itemId = 1l;
        Long deliveryId = 1l;
        UpdateItemDto.Request updateItemRequestDto = UpdateItemDto.Request.builder()
                .itemId(itemId)
                .itemName("업데이트 상품")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("업데이트 상품 상세")
                .price(3000)
                .stockNumber(100)
                .deliveryId(deliveryId)
                .build();

        Delivery delivery = Delivery.builder()
                .deliveryId(deliveryId)
                .deliveryFee(3000)
                .deliveryName("마포구 물류센터")
                .build();

        Item savedItem = Item.builder()
                .itemName(updateItemRequestDto.getItemName())
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail(updateItemRequestDto.getItemDetail())
                .price(3000)
                .stockNumber(100)
                .build();
        savedItem.updateDelivery(delivery);

        Item updateItem = updateItemRequestDto.toEntity();
        Mockito.when(itemService.updateItem(itemId, updateItem)).thenReturn(savedItem);
        Mockito.when(deliveryService.findByDeliveryId(updateItemRequestDto.getDeliveryId())).thenReturn(delivery);

        // when
        UpdateItemDto.Response response = apiItemService.updateItem(updateItemRequestDto);

        // then
        Assertions.assertThat(response.getItemName()).isEqualTo(updateItemRequestDto.getItemName());
        Assertions.assertThat(response.getItemSellStatus()).isEqualTo(updateItemRequestDto.getItemSellStatus());
        Assertions.assertThat(response.getItemDetail()).isEqualTo(updateItemRequestDto.getItemDetail());
        Assertions.assertThat(response.getPrice()).isEqualTo(updateItemRequestDto.getPrice());
        Assertions.assertThat(response.getStockNumber()).isEqualTo(updateItemRequestDto.getStockNumber());
    }

}
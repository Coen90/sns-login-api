package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateItemDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Request {

        @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
        private Long itemId;

        @NotBlank(message = "상품명은 필수 입력 값입니다.")
        private String itemName;

        @NotNull(message = "가격은 필수 입력 값입니다.")
        private Integer price;

        @ApiModelProperty(value = "상품 상세", required = true, example = "테스트 상품 상세")
        private String itemDetail;

        @NotNull(message = "재고는 필수 입력 값입니다.")
        private Integer stockNumber;

        @NotNull(message = "판매상태는 필수 입력 값입니다. (SELL OR SOLD_OUT)")
        private ItemSellStatus itemSellStatus;

        @NotNull(message = "배송 정보는 필수 입력 값입니다.")
        private Long deliveryId;

        public Item toEntity() {
            return Item.builder()
                    .itemName(itemName)
                    .price(price)
                    .itemDetail(itemDetail)
                    .stockNumber(stockNumber)
                    .itemSellStatus(itemSellStatus)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {

        private Long itemId;
        private String itemName;
        private Integer price;
        private String itemDetail;
        private Integer stockNumber;
        private ItemSellStatus itemSellStatus;
        private Long deliveryId;

        public static Response of(Item item) {
            return UpdateItemDto.Response.builder()
                    .itemId(item.getItemId())
                    .itemName(item.getItemName())
                    .itemDetail(item.getItemDetail())
                    .itemSellStatus(item.getItemSellStatus())
                    .stockNumber(item.getStockNumber())
                    .price(item.getPrice())
                    .deliveryId(item.getDelivery().getDeliveryId())
                    .build();
        }
    }

}

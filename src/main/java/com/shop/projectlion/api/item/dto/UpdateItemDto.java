package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import io.swagger.annotations.ApiModel;
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
    @ApiModel(value = "상품 업데이트 API 요청 객체", description = "상품 업데이트 API 요청 객체")
    public static class Request {

        @ApiModelProperty(value = "상품 아이디", required = true, example = "2")
        @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
        private Long itemId;

        @ApiModelProperty(value = "상품명", required = true, example = "테스트 상품명")
        @NotBlank(message = "상품명은 필수 입력 값입니다.")
        private String itemName;

        @ApiModelProperty(value = "가격", required = true, example = "30000")
        @NotNull(message = "가격은 필수 입력 값입니다.")
        private Integer price;

        @ApiModelProperty(value = "상품 상세", required = true, example = "테스트 상품 상세")
        private String itemDetail;

        @ApiModelProperty(value = "재고", required = true, example = "999")
        @NotNull(message = "재고는 필수 입력 값입니다.")
        private Integer stockNumber;

        @ApiModelProperty(value = "판매 상태", required = true, example = "SELL")
        @NotNull(message = "판매상태는 필수 입력 값입니다. (SELL OR SOLD_OUT)")
        private ItemSellStatus itemSellStatus;

        @ApiModelProperty(value = "배송아이디", required = true, example = "3")
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
    @ApiModel(value = "상품 업데이트 API 반환 객체", description = "상품 업데이트 API 반환 객체")
    public static class Response {

        @ApiModelProperty(value = "상품 아이디", required = true, example = "1")
        private Long itemId;

        @ApiModelProperty(value = "상품명", required = true, example = "테스트 상품명")
        private String itemName;

        @ApiModelProperty(value = "가격", required = true, example = "30000")
        private Integer price;

        @ApiModelProperty(value = "상품 상세", required = true, example = "테스트 상품 상세")
        private String itemDetail;

        @ApiModelProperty(value = "재고", required = true, example = "999")
        private Integer stockNumber;

        @ApiModelProperty(value = "판매 상태", required = true, example = "SELL")
        private ItemSellStatus itemSellStatus;

        @ApiModelProperty(value = "배송아이디", required = true, example = "3")
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

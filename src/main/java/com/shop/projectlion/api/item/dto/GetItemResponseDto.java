package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetItemResponseDto {

    private Long itemId;
    private String itemName;
    private int price;
    private String itemDetail;
    private int stockNumber;
    private ItemSellStatus itemSellStatus;
    private Long deliveryId;
    private int deliveryFee;
    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemImageDto {
        private Long itemImageId;
        private String imageUrl;

        public static ItemImageDto of(ItemImage itemImage) {
            return GetItemResponseDto.ItemImageDto.builder()
                    .itemImageId(itemImage.getItemImageId())
                    .imageUrl(itemImage.getImageUrl())
                    .build();
        }
    }

    public static GetItemResponseDto of(Item item, List<ItemImage> itemImages) {
        List<ItemImageDto> itemImageDtos = itemImages.stream()
                .map(itemImage -> GetItemResponseDto.ItemImageDto.of(itemImage))
                .collect(Collectors.toList());

        return GetItemResponseDto.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .price(item.getPrice())
                .deliveryId(item.getDelivery().getDeliveryId())
                .deliveryFee(item.getDelivery().getDeliveryFee())
                .stockNumber(item.getStockNumber())
                .itemImageDtos(itemImageDtos)
                .build();
    }

}

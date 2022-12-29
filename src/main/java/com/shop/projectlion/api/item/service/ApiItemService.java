package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.GetItemResponseDto;
import com.shop.projectlion.api.item.dto.UpdateItemDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import com.shop.projectlion.domain.itemimage.service.ItemImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiItemService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final DeliveryService deliveryService;

    public GetItemResponseDto getItem(Long itemId) {
        Item item = itemService.findByItemIdFetchDelivery(itemId);
        List<ItemImage> itemImages = itemImageService.findByItemOrderByItemImageIdAsc(item);
        GetItemResponseDto getItemResponseDto = GetItemResponseDto.of(item, itemImages);
        return getItemResponseDto;
    }

    @Transactional
    public UpdateItemDto.Response updateItem(UpdateItemDto.Request updateItemRequestDto) {

        // 1. 상품 정보 업데이트
        Item updateItem = updateItemRequestDto.toEntity();
        Item savedItem = itemService.updateItem(updateItemRequestDto.getItemId(), updateItem);

        // 2. 배송 업데이트
        Delivery delivery = deliveryService.findByDeliveryId(updateItemRequestDto.getDeliveryId());
        savedItem.updateDelivery(delivery);

        return UpdateItemDto.Response.of(savedItem);
    }

}

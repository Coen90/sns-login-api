package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.GetItemResponseDto;
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

}

package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item registerItem(Item item) {
        itemRepository.save(item);
        return item;
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ITEM_NOT_EXISTS));
    }

    @Transactional
    public Item updateItem(Long itemId, Item updateItem) {
        Item savedItem = findByItemId(itemId);
        savedItem.updateItem(updateItem);
        return savedItem;
    }

    public Item findByItemIdFetchDelivery(Long itemId) {
        return itemRepository.findByItemIdFetchDelivery(itemId);
    }

}
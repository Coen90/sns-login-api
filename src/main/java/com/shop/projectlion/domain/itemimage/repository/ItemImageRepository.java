package com.shop.projectlion.domain.itemimage.repository;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    List<ItemImage> findByItemOrderByItemImageIdAsc(Item item);

    ItemImage findByItemAndIsRepImage(Item item, boolean isRepImage);


}
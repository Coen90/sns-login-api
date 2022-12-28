package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i " +
            "from Item i join fetch i.delivery " +
            "where  i.itemId = :itemId")
    Item findByItemIdFetchDelivery(@Param("itemId") Long itemId);


}